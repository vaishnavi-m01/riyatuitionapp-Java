package com.riyatuition.riya_tuition.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({
    "id",
    "name",
    "phone",
    "school",
    "standardId",
    "standardName",
    "mediumId",
    "mediumName",
    "age",
    "gender",
    "dateOfBirth",
    "joiningDate",
    "address",
    "imageUrl",
    "activeStatus",
    "createdDate",
    "modifiedDate"
})
public class StudentModel {

    private Integer id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
  
	private String phone;
    
    @NotBlank(message = "School is required")
    private String school;

    // Standard info
    @NotNull(message = "StandardId is required")

    private Integer standardId;
    private String standardName;

    // Medium info
    @NotNull(message = "MediumId is required")

    private Integer mediumId;
    private String mediumName;

    private Integer age;
    
    @NotBlank(message = "Gender is required")
    private String gender;

    private LocalDate dateOfBirth;
    private LocalDate joiningDate;

    private String address;
    private Boolean activeStatus;
    private String imageUrl;


    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
