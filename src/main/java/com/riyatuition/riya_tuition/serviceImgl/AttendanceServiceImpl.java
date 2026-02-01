	package com.riyatuition.riya_tuition.serviceImgl;
	
	import org.springframework.stereotype.Service;
	
	import com.riyatuition.riya_tuition.entity.AttendanceEntity;
	import com.riyatuition.riya_tuition.entity.StudentEntity;
	import com.riyatuition.riya_tuition.enums.AttendanceStatus;
	import com.riyatuition.riya_tuition.model.AttendanceModel;
	import com.riyatuition.riya_tuition.model.AttendanceResponse;
	import com.riyatuition.riya_tuition.repository.AttendanceRepository;
	import com.riyatuition.riya_tuition.repository.StudentRepo;
	import com.riyatuition.riya_tuition.service.AttendanceService;
	
	import java.time.LocalDate;
	import java.util.List;
	import java.util.stream.Collectors;
	
	@Service
	public class AttendanceServiceImpl implements AttendanceService {
	
		 private final AttendanceRepository repository;
		    private final StudentRepo studentRepository;
		    
	
		    public AttendanceServiceImpl(AttendanceRepository repository, StudentRepo studentRepository) {
		        this.repository = repository;
		        this.studentRepository = studentRepository; // assign
		    }
	
		    @Override
		    public AttendanceModel create(AttendanceModel model) {
	
		        AttendanceEntity entity = new AttendanceEntity();
	
		        // Fetch student from studentId
		        StudentEntity student = studentRepository.findById(model.getStudentId())
		                .orElseThrow(() -> new RuntimeException("Student not found"));
	
		        entity.setStudent(student); 
		        entity.setStatus(model.getStatus());
		        entity.setDate(model.getDate());
	
		        AttendanceEntity saved = repository.save(entity);
		        return mapToModel(saved);
		    }
	
	    @Override
	    public AttendanceModel getById(Integer id) {
	        AttendanceEntity entity = repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Attendance not found"));
	        return mapToModel(entity);
	    }
	
	//    @Override
	//    public List<AttendanceModel> getAll() {
	//        return repository.findAll()
	//                .stream()
	//                .map(this::mapToModel)
	//                .collect(Collectors.toList());
	//    }
	    
	    
	    
	    @Override
	    public AttendanceResponse getAll(LocalDate date) {
	
	        // 1️⃣ Get attendance entities from repository
	        List<AttendanceEntity> entities;
	        if (date != null) {
	            entities = repository.findByDate(date);
	        } else {
	            entities = repository.findAll();
	        }
	
	        // 2️⃣ Convert entities → models
	        List<AttendanceModel> models = entities.stream()
	                .map(this::mapToModel)
	                .toList();
	
	        // 3️⃣ Calculate present and absent counts from the list
	        long presentCount = models.stream()
	                .filter(a -> AttendanceStatus.Present.equals(a.getStatus()))
	                .count();
	
	        long absentCount = models.stream()
	                .filter(a -> AttendanceStatus.Absent.equals(a.getStatus()))
	                .count();
	
	        // 4️⃣ Prepare response
	        AttendanceResponse response = new AttendanceResponse();
	        response.setAttendanceList(models);
	        response.setPresentCount(presentCount);
	        response.setAbsentCount(absentCount);
	
	        return response;
	    }
	
	    
	
	    @Override
	    public AttendanceModel update(Integer id, AttendanceModel model) {
	
	        // 1️⃣ Fetch the record to update
	        AttendanceEntity entity = repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Attendance not found"));
	
	        // 2️⃣ Fetch the student
	        StudentEntity student = studentRepository.findById(model.getStudentId())
	                .orElseThrow(() -> new RuntimeException("Student not found"));
	
	        // 3️⃣ Check for existing attendance for the same student and date (excluding current record)
	        repository.findByStudentIdAndDate(student.getId(), model.getDate())
	                .ifPresent(existing -> {
	                    if (!existing.getId().equals(id)) {
	                        throw new RuntimeException("Attendance for this student on this date already exists!");
	                    }
	                });
	
	        // 4️⃣ Update fields
	        entity.setStudent(student);
	        entity.setStatus(model.getStatus());
	        entity.setDate(model.getDate());
	
	        repository.save(entity);
	
	        return mapToModel(entity);
	    }
	
	
	
	    @Override
	    public void delete(Integer id) {
	        AttendanceEntity entity = repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Attendance not found"));
	        repository.delete(entity);
	    }
	
	
	    private AttendanceModel mapToModel(AttendanceEntity entity) {
	        AttendanceModel model = new AttendanceModel();
	        model.setId(entity.getId());
	        model.setStudentId(entity.getStudent().getId());
	        model.setName(entity.getStudent().getName());  
	        model.setStatus(entity.getStatus());
	        model.setDate(entity.getDate());
	        return model;
	    }
	
	}
