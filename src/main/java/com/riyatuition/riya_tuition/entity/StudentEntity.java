package com.riyatuition.riya_tuition.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "students")			
@EntityListeners(AuditingEntityListener.class)

public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name")
    private String name;

    private String school;

    private Integer age;

    @NotNull
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "standard_id")
    private StandardEntity standard;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "medium_id")
    private MediumEntity medium;
}
