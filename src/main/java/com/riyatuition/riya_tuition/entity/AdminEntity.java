package com.riyatuition.riya_tuition.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@Entity
@Table(name = "admin")
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({"id","name","email","phone","image","createdDate","modifiedDate"})
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    private String password;

    private String image; // image path (optional)

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
