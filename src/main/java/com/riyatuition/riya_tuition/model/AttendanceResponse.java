package com.riyatuition.riya_tuition.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "presentCount", "absentCount", "attendanceList" })

public class AttendanceResponse {

    private long presentCount;
    private long absentCount;
    private List<AttendanceModel> attendanceList;

}
