package com.riyatuition.riya_tuition.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mediums")
public class MediumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "modifiedDate")
    private LocalDateTime modifiedDate;
}
