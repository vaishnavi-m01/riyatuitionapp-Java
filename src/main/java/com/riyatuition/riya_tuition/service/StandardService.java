package com.riyatuition.riya_tuition.service;

import java.util.List;
import com.riyatuition.riya_tuition.model.StandardModel;


public interface StandardService {
	
	StandardModel createStandard(StandardModel model);
	
	List<StandardModel> getAllStandard();
	
	StandardModel getStandardByid(Integer id);
	
	StandardModel updateStandard(Integer id, StandardModel model);
	
	String deleteStandard(Integer id);
}
