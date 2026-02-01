package com.riyatuition.riya_tuition.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateRangeUtil {

    private DateRangeUtil() {}

    public static LocalDateTime[] getDateRange(String period) {

        ZoneId zone = ZoneId.of("Asia/Kolkata");
        LocalDate today = LocalDate.now(zone);

        switch (period.toLowerCase()) {

            case "today":
                return new LocalDateTime[]{
                        today.atStartOfDay(),
                        today.atTime(23, 59, 59)
                };

            case "weekly":
                return new LocalDateTime[]{
                        today.with(DayOfWeek.MONDAY).atStartOfDay(),
                        today.with(DayOfWeek.SUNDAY).atTime(23, 59, 59)
                };

            case "monthly":
                return new LocalDateTime[]{
                        today.withDayOfMonth(1).atStartOfDay(),
                        today.withDayOfMonth(today.lengthOfMonth()).atTime(23, 59, 59)
                };

            case "yearly":
                return new LocalDateTime[]{
                        today.withDayOfYear(1).atStartOfDay(),
                        today.withDayOfYear(today.lengthOfYear()).atTime(23, 59, 59)
                };

            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}
