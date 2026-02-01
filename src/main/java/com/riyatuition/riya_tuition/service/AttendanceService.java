package com.riyatuition.riya_tuition.service;

import com.riyatuition.riya_tuition.model.AttendanceModel;
import com.riyatuition.riya_tuition.model.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

	AttendanceModel create(AttendanceModel model);

	AttendanceModel getById(Integer id);

//	List<AttendanceModel> getAll();

	AttendanceResponse getAll(LocalDate date);

	AttendanceModel update(Integer id, AttendanceModel model);

	void delete(Integer id);

}
