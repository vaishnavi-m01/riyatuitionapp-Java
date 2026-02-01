package com.riyatuition.riya_tuition.serviceImgl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.enums.PaymentType;
import com.riyatuition.riya_tuition.repository.FeesRepository;
import com.riyatuition.riya_tuition.utils.DateRangeUtil;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FeesReportService {

    private final FeesRepository feesRepository;

    public Map<String, Object> getFeesSummary(String period, String paymentMethod) {

        LocalDateTime[] range = DateRangeUtil.getDateRange(period);

        Object result = feesRepository.getPaidSummary(
                range[0],
                range[1],
                PaymentType.CASH,
                PaymentType.UPI
        );

        System.out.println("RAW RESULT CLASS = " + result.getClass());

        Object[] outer = (Object[]) result;
        System.out.println("OUTER = " + Arrays.toString(outer));


        Object[] row = (Object[]) outer[0];
        System.out.println("ROW = " + Arrays.toString(row));

        BigDecimal totalAmount = toBigDecimal(row[0]);
        BigDecimal cashAmount  = toBigDecimal(row[1]);
        BigDecimal upiAmount   = toBigDecimal(row[2]);

        System.out.println("TOTAL = " + totalAmount);
        System.out.println("CASH  = " + cashAmount);
        System.out.println("UPI   = " + upiAmount);


        BigDecimal displayAmount;
        if ("cash".equalsIgnoreCase(paymentMethod)) {
            displayAmount = cashAmount;
        } else if ("upi".equalsIgnoreCase(paymentMethod)) {
            displayAmount = upiAmount;
        } else {
            displayAmount = totalAmount;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalAmount", totalAmount);
        response.put("displayAmount", displayAmount);

        return response;
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) return BigDecimal.ZERO;
        if (value instanceof BigDecimal bd) return bd;
        if (value instanceof Number n) return BigDecimal.valueOf(n.doubleValue());
        return BigDecimal.ZERO;
    }
}
