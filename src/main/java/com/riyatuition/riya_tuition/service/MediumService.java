package com.riyatuition.riya_tuition.service;

import java.util.List;

import com.riyatuition.riya_tuition.model.MediumModel;

public interface MediumService {
  
	MediumModel createMedium(MediumModel model);
	
	List<MediumModel> getAll();
	
	MediumModel getById(Integer  id);
	
	MediumModel updateMedium(Integer id, MediumModel model)	;
	
	String deleteMedium(Integer id);
	
}
