package com.riyatuition.riya_tuition.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeesSummaryModel {
    private BigDecimal totalAmount;
    private BigDecimal totalPaid;
    private BigDecimal totalPending;
    private long paidCount;
    private long pendingCount;
}
