package com.riyatuition.riya_tuition.serviceImgl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.riyatuition.riya_tuition.entity.MediumEntity;
import com.riyatuition.riya_tuition.entity.StandardEntity;
import com.riyatuition.riya_tuition.entity.StudentEntity;
import com.riyatuition.riya_tuition.model.StudentModel;
import com.riyatuition.riya_tuition.repository.MediumRepo;
import com.riyatuition.riya_tuition.repository.StandardRepo;
import com.riyatuition.riya_tuition.repository.StudentRepo;
import com.riyatuition.riya_tuition.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StandardRepo standardRepo;

    @Autowired
    private MediumRepo mediumRepo;

    @Value("${supabase.url}")
    private String SUPABASE_URL;

    @Value("${supabase.service-key}")
    private String SUPABASE_SERVICE_KEY;

    // ================= CREATE =================
    @Override
    public StudentModel createStudent(StudentModel model, MultipartFile image) {

        StandardEntity standard = standardRepo.findById(model.getStandardId())
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        MediumEntity medium = mediumRepo.findById(model.getMediumId())
                .orElseThrow(() -> new RuntimeException("Medium not found"));

        StudentEntity student = new StudentEntity();
        student.setName(model.getName());
        student.setPhone(model.getPhone());
        student.setSchool(model.getSchool());
        student.setAge(model.getAge());
        student.setGender(model.getGender());
        student.setDateOfBirth(model.getDateOfBirth());
        student.setJoiningDate(model.getJoiningDate());
        student.setAddress(model.getAddress());
        student.setActiveStatus(model.getActiveStatus());
        student.setCreatedDate(LocalDateTime.now());
        student.setModifiedDate(LocalDateTime.now());
        student.setStandard(standard);
        student.setMedium(medium);

        StudentEntity savedStudent = studentRepo.save(student);

        // ✅ Upload image after ID generated
        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadToSupabase(image, savedStudent.getId());
            savedStudent.setImageUrl(imageUrl);
            studentRepo.save(savedStudent);
        }

        return mapToModel(savedStudent);
    }

    // ================= UPDATE =================
    @Override
    public StudentModel updateStudent(Integer id, StudentModel model, MultipartFile image) {

        StudentEntity student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(model.getName());
        student.setPhone(model.getPhone());
        student.setSchool(model.getSchool());
        student.setAge(model.getAge());
        student.setGender(model.getGender());
        student.setDateOfBirth(model.getDateOfBirth());
        student.setJoiningDate(model.getJoiningDate());
        student.setAddress(model.getAddress());
        student.setActiveStatus(model.getActiveStatus());
        student.setModifiedDate(LocalDateTime.now());

        student.setStandard(
                standardRepo.findById(model.getStandardId()).orElse(null));
        student.setMedium(
                mediumRepo.findById(model.getMediumId()).orElse(null));

        // ✅ overwrite image
        if (image != null && !image.isEmpty()) {
            student.setImageUrl(uploadToSupabase(image, id));
        }

        return mapToModel(studentRepo.save(student));
    }

    // ================= SUPABASE UPLOAD =================
    private String uploadToSupabase(MultipartFile file, Integer studentId) {

        try {
            String fileName = "Student_" + studentId + ".png";

            String uploadUrl =
                    SUPABASE_URL + "/storage/v1/object/Student_image/" + fileName;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setBearerAuth(SUPABASE_SERVICE_KEY);

            HttpEntity<byte[]> request =
                    new HttpEntity<>(file.getBytes(), headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange(uploadUrl, HttpMethod.PUT, request, String.class);

            return SUPABASE_URL +
                    "/storage/v1/object/public/Student_image/" +
                    fileName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Student image upload failed");
        }
    }

    // ================= MAPPER =================
    private StudentModel mapToModel(StudentEntity entity) {

        StudentModel model = new StudentModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPhone(entity.getPhone());
        model.setSchool(entity.getSchool());
        model.setAge(entity.getAge());
        model.setGender(entity.getGender());
        model.setDateOfBirth(entity.getDateOfBirth());
        model.setJoiningDate(entity.getJoiningDate());
        model.setAddress(entity.getAddress());
        model.setActiveStatus(entity.getActiveStatus());
        model.setImageUrl(entity.getImageUrl());
        model.setCreatedDate(entity.getCreatedDate());
        model.setModifiedDate(entity.getModifiedDate());

        if (entity.getStandard() != null) {
            model.setStandardId(entity.getStandard().getId());
            model.setStandardName(entity.getStandard().getName());
        }

        if (entity.getMedium() != null) {
            model.setMediumId(entity.getMedium().getId());
            model.setMediumName(entity.getMedium().getName());
        }

        return model;
    }

    // ================= OTHERS =================
    public List<StudentModel> getAllStudents() {
        return studentRepo.findAll().stream().map(this::mapToModel).toList();
    }

    public StudentModel getStudentById(Integer id) {
        return mapToModel(studentRepo.findById(id).orElse(null));
    }

    public String deleteStudent(Integer id) {
        studentRepo.deleteById(id);
        return "Student deleted successfully";
    }
}
