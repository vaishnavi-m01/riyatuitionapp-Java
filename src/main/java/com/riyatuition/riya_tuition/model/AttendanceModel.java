package com.riyatuition.riya_tuition.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.riyatuition.riya_tuition.enums.AttendanceStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "studentId", "name", "status", "date" })

public class AttendanceModel {

	private Integer id;

	@NotNull(message = "Student id is required")
	private Integer studentId;

    private String name;  


	@NotNull(message = "Status is required (Present / Absent)")
	private AttendanceStatus status;

	@NotNull(message = "Date is required")
	private LocalDate date;
}
