package com.riyatuition.riya_tuition.serviceImgl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riyatuition.riya_tuition.entity.ClassFeesEntity;
import com.riyatuition.riya_tuition.entity.FeesEntity;
import com.riyatuition.riya_tuition.entity.StudentEntity;
import com.riyatuition.riya_tuition.model.FeesModel;
import com.riyatuition.riya_tuition.repository.ClassFeesRepository;
import com.riyatuition.riya_tuition.repository.FeesRepository;
import com.riyatuition.riya_tuition.repository.StudentRepo;
import com.riyatuition.riya_tuition.service.FeesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeesServiceImpl implements FeesService {

        private final FeesRepository feesRepo;
        private final StudentRepo studentRepo;
        private final ClassFeesRepository classFeesRepo;

        @Override
        @Transactional
        public FeesModel createFees(FeesModel model) {
                // 1. Check if payment already exists for this student in current month
                LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0)
                                .withSecond(0)
                                .withNano(0);
                LocalDateTime endOfMonth = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).minusNanos(1);

                List<FeesEntity> existingFees = feesRepo.findByCreatedDateBetween(startOfMonth, endOfMonth);
                boolean alreadyPaid = existingFees.stream()
                                .anyMatch(f -> f.getStudentId().equals(model.getStudentId()));

                if (alreadyPaid) {
                        String monthName = LocalDateTime.now().getMonth().name().toLowerCase();
                        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
                        throw new RuntimeException("Fees for this student already paid for " + monthName + ".");
                }

                // 2. Fetch student and class fees to calculate balance
                StudentEntity student = studentRepo.findById(model.getStudentId())
                                .orElseThrow(() -> new RuntimeException("Student not found"));

                ClassFeesEntity classFee = classFeesRepo.findByStandardId(student.getStandard().getId())
                                .orElseThrow(() -> new RuntimeException("Class fees not configured for this standard"));

                BigDecimal totalAmount = classFee.getAmount();
                BigDecimal paidAmount = model.getPaid();
                BigDecimal pendingAmount = totalAmount.subtract(paidAmount);
                String status = pendingAmount.compareTo(BigDecimal.ZERO) <= 0 ? "Paid" : "Pending";

                // 3. Create Entity
                FeesEntity entity = FeesEntity.builder()
                                .studentId(model.getStudentId())
                                .initialFeesId(classFee.getId())
                                .paid(paidAmount)
                                .pending(pendingAmount)
                                .status(status)
                                .paymentType(model.getPaymentType())
                                .createdDate(LocalDateTime.now())
                                .modifiedDate(LocalDateTime.now())
                                .build();

                FeesEntity saved = feesRepo.save(entity);
                return mapToModel(saved);
        }

        @Override
        public List<FeesModel> getAllFees() {
                return feesRepo.findAll().stream()
                                .map(this::mapToModel)
                                .collect(Collectors.toList());
        }

        @Override
        public FeesModel getFeesById(Integer id) {
                return feesRepo.findById(id)
                                .map(this::mapToModel)
                                .orElseThrow(() -> new RuntimeException("Fees record not found"));
        }

        @Override
        @Transactional
        public FeesModel updateFees(Integer id, FeesModel model) {
                FeesEntity entity = feesRepo.findById(id)
                                .orElseThrow(() -> new RuntimeException("Fees record not found"));

                entity.setPaid(model.getPaid());
                entity.setPaymentType(model.getPaymentType());
                entity.setStatus(model.getStatus());
                entity.setModifiedDate(LocalDateTime.now());

                FeesEntity saved = feesRepo.save(entity);
                return mapToModel(saved);
        }

        @Override
        public String deleteFees(Integer id) {
                feesRepo.deleteById(id);
                return "Fees record deleted successfully";
        }

        @Override
        @Transactional
        public FeesModel settleBalance(FeesModel model) {
                // Find existing pending fee for this student (most recent pending)
                List<FeesEntity> studentFees = feesRepo.findAll().stream()
                                .filter(f -> f.getStudentId().equals(model.getStudentId()))
                                .sorted((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()))
                                .collect(Collectors.toList());

                FeesEntity recentFee = studentFees.isEmpty() ? null : studentFees.get(0);

                if (recentFee == null || !"Pending".equalsIgnoreCase(recentFee.getStatus())) {
                        throw new RuntimeException("No pending balance found for this student.");
                }

                BigDecimal additionalPaid = model.getPaid();
                BigDecimal newPending = recentFee.getPending().subtract(additionalPaid);

                recentFee.setPaid(recentFee.getPaid().add(additionalPaid));
                recentFee.setPending(newPending.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newPending);
                recentFee.setStatus(recentFee.getPending().compareTo(BigDecimal.ZERO) <= 0 ? "Paid" : "Pending");
                recentFee.setModifiedDate(LocalDateTime.now());

                return mapToModel(feesRepo.save(recentFee));
        }

        @Override
        public List<FeesModel> getRecentTransactions(Integer studentId) {
                return feesRepo.findAll().stream()
                                .filter(f -> f.getStudentId().equals(studentId))
                                .sorted((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()))
                                .map(this::mapToModel)
                                .collect(Collectors.toList());
        }

        private FeesModel mapToModel(FeesEntity entity) {
                StudentEntity student = studentRepo.findById(entity.getStudentId()).orElse(null);
                String studentName = (student != null) ? student.getName() : "Unknown";
                String standardName = (student != null && student.getStandard() != null)
                                ? student.getStandard().getName()
                                : "N/A";

                // Find class fee amount for reference
                ClassFeesEntity cf = classFeesRepo.findById(entity.getInitialFeesId()).orElse(null);
                BigDecimal totalAmount = (cf != null) ? cf.getAmount() : entity.getPaid().add(entity.getPending());

                return FeesModel.builder()
                                .id(entity.getId())
                                .studentId(entity.getStudentId())
                                .studentName(studentName)
                                .standardName(standardName)
                                .initialFeesId(entity.getInitialFeesId())
                                .amount(totalAmount)
                                .paid(entity.getPaid())
                                .pending(entity.getPending())
                                .status(entity.getStatus())
                                .paymentType(entity.getPaymentType())
                                .createdDate(entity.getCreatedDate())
                                .modifiedDate(entity.getModifiedDate())
                                .build();
        }
}
