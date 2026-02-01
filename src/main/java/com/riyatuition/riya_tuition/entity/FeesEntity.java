package com.riyatuition.riya_tuition.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.riyatuition.riya_tuition.enums.PaymentType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class FeesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "initial_fees_id")
    private Integer initialFeesId;


    private BigDecimal paid;

    private BigDecimal pending;

    private String status;   // Paid / Pending

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;


    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
