package com.riyatuition.riya_tuition.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.riyatuition.riya_tuition.enums.PaymentType;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({ "id", "studentId","studentName","standardName", "initialFeesId", "amount", "paid","pending","status","paymentType","createdDate", "modifiedDate" })

public class FeesModel {

    private Integer id;
    private Integer studentId;
    private String studentName;
    private String standardName;
    private Integer initialFeesId;
    private BigDecimal amount; 
    private BigDecimal paid;
    private BigDecimal pending;
    private String status;
    private PaymentType paymentType;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
