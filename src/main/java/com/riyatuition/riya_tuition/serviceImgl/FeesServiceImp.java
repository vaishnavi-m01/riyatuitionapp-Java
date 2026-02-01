package com.riyatuition.riya_tuition.serviceImgl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.entity.ClassFeesEntity;
import com.riyatuition.riya_tuition.entity.FeesEntity;
import com.riyatuition.riya_tuition.enums.PaymentType;
import com.riyatuition.riya_tuition.model.FeesModel;
import com.riyatuition.riya_tuition.repository.ClassFeesRepository;
import com.riyatuition.riya_tuition.repository.FeesRepository;
import com.riyatuition.riya_tuition.repository.StudentRepo;
import com.riyatuition.riya_tuition.service.FeesService;

@Service
public class FeesServiceImp implements FeesService {

    @Autowired
    private FeesRepository feesRepo;

    @Autowired
    private ClassFeesRepository initialFeesRepo;

    @Autowired
    private StudentRepo studentRepo;

    // ENTITY → MODEL
    private FeesModel mapToModel(FeesEntity entity) {
        FeesModel model = new FeesModel();
        model.setId(entity.getId());
        model.setStudentId(entity.getStudentId());
        model.setInitialFeesId(entity.getInitialFeesId());

        // Fetch base amount from initial_fees
        BigDecimal baseAmount = initialFeesRepo.findById(entity.getInitialFeesId())
                .map(ClassFeesEntity::getAmount)
                .orElse(BigDecimal.ZERO);
        model.setAmount(baseAmount);

        // Calculate pending dynamically
        BigDecimal pending = baseAmount.subtract(entity.getPaid());
        model.setPaid(entity.getPaid());
        model.setPending(pending);
        model.setStatus(pending.compareTo(BigDecimal.ZERO) == 0 ? "Paid" : "Pending");

        model.setPaymentType(entity.getPaymentType());
        model.setCreatedDate(entity.getCreatedDate());
        model.setModifiedDate(entity.getModifiedDate());

        // Fetch student info
        studentRepo.findById(entity.getStudentId()).ifPresent(student -> {
            model.setStudentName(student.getName());
            if (student.getStandard() != null) {
                model.setStandardName(student.getStandard().getName());
            }
        });

        return model;
    }

    @Override
    public FeesModel createFees(FeesModel model) {
        BigDecimal baseAmount = initialFeesRepo.findById(model.getInitialFeesId())
                .map(ClassFeesEntity::getAmount)
                .orElse(BigDecimal.ZERO);

        BigDecimal pending = baseAmount.subtract(model.getPaid());

        FeesEntity entity = new FeesEntity();
        entity.setStudentId(model.getStudentId());
        entity.setInitialFeesId(model.getInitialFeesId());
        entity.setPaid(model.getPaid());
        entity.setPending(pending);
        entity.setStatus(pending.compareTo(BigDecimal.ZERO) == 0 ? "Paid" : "Pending");
        entity.setPaymentType(model.getPaymentType());
        entity.setCreatedDate(LocalDateTime.now());

        return mapToModel(feesRepo.save(entity));
    }

    @Override
    public List<FeesModel> getAll(String dateStr) {
        List<FeesEntity> allFees = feesRepo.findAll();

        if (dateStr == null || dateStr.isEmpty()) {
            return allFees.stream()
                          .map(this::mapToModel)
                          .toList();
        }

        LocalDate filterDate = LocalDate.parse(dateStr);

        return allFees.stream()
                .filter(fee -> {
                    LocalDate created = fee.getCreatedDate().toLocalDate();
                    LocalDate modified = fee.getModifiedDate() != null ? fee.getModifiedDate().toLocalDate() : null;
                    return created.equals(filterDate) || (modified != null && modified.equals(filterDate));
                })
                .map(this::mapToModel)
                .toList();
    }

    @Override
    public FeesModel getById(Integer id) {
        return feesRepo.findById(id)
                .map(this::mapToModel)
                .orElse(null);
    }

    @Override
    public FeesModel updateFees(Integer id, FeesModel model) {

        FeesEntity entity = feesRepo.findById(id).orElse(null);
        if (entity == null) return null;

        entity.setStudentId(model.getStudentId());
        entity.setInitialFeesId(model.getInitialFeesId());
        entity.setPaid(model.getPaid()); 

        BigDecimal baseAmount = initialFeesRepo.findById(model.getInitialFeesId())
                .map(ClassFeesEntity::getAmount)
                .orElse(BigDecimal.ZERO);

        BigDecimal pending = baseAmount.subtract(model.getPaid());
        entity.setPending(pending);
        entity.setStatus(pending.compareTo(BigDecimal.ZERO) == 0 ? "Paid" : "Pending");
        entity.setPaymentType(model.getPaymentType());
        entity.setModifiedDate(LocalDateTime.now());

        return mapToModel(feesRepo.save(entity));
    }

    
    
    @Override
    public FeesModel settleBalance(Integer id, BigDecimal paid, PaymentType paymentType) {

        FeesEntity entity = feesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fees record not found"));

        // Fetch base amount from class fees
        BigDecimal baseAmount = initialFeesRepo.findById(entity.getInitialFeesId())
                .map(ClassFeesEntity::getAmount)
                .orElse(BigDecimal.ZERO);

        // Add new payment to existing paid
        BigDecimal totalPaid = entity.getPaid().add(paid);

        // Prevent over payment
        if (totalPaid.compareTo(baseAmount) > 0) {
            throw new RuntimeException("Paid amount exceeds total fees");
        }

        // Calculate pending
        BigDecimal pending = baseAmount.subtract(totalPaid);

        // Update entity
        entity.setPaid(totalPaid);
        entity.setPending(pending);
        entity.setStatus(
                pending.compareTo(BigDecimal.ZERO) == 0 ? "Paid" : "Pending"
        );
        entity.setPaymentType(paymentType);
        entity.setModifiedDate(LocalDateTime.now());

        return mapToModel(feesRepo.save(entity));
    }



    @Override
    public String deleteFees(Integer id) {
        feesRepo.deleteById(id);
        return "Fees deleted successfully";
    }
}
