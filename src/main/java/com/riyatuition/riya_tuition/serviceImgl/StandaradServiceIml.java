package com.riyatuition.riya_tuition.serviceImgl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.entity.StandardEntity;
import com.riyatuition.riya_tuition.model.StandardModel;
import com.riyatuition.riya_tuition.repository.StandardRepo;
import com.riyatuition.riya_tuition.service.StandardService;

@Service
public class StandaradServiceIml implements StandardService {

	@Autowired
	private StandardRepo standardRepo;

	private StandardModel maptoModel(StandardEntity entity) {

		if (entity == null)
			return null;

		StandardModel model = new StandardModel();
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setCreatedDate(entity.getCreatedDate());
		model.setModifiedDate(entity.getModifiedDate());
		return model;
	}

	@Override
	public StandardModel createStandard(StandardModel model) {

		StandardEntity entity = new StandardEntity();

		entity.setName(model.getName());
		entity.setCreatedDate(model.getCreatedDate());
		entity.setModifiedDate(model.getModifiedDate());

		StandardEntity savedstandard = standardRepo.save(entity);
		return maptoModel(savedstandard);
	}

	@Override
	public List<StandardModel> getAllStandard() {
		return standardRepo.findAll().stream().map(this::maptoModel).toList();
	}

	@Override
	public StandardModel getStandardByid(Integer id) {

		StandardEntity entity = standardRepo.findById(id).orElse(null);
		return maptoModel(entity);
	}

	@Override
	public StandardModel updateStandard(Integer id, StandardModel model) {

		StandardEntity entity = standardRepo.findById(id).orElse(null);

		if (entity == null)
			return null;

		entity.setId(model.getId());
		entity.setName(model.getName());
		entity.setModifiedDate(model.getModifiedDate());

		StandardEntity update = standardRepo.save(entity);
		return maptoModel(update);

	}

	@Override
	public String deleteStandard(Integer id) {
		standardRepo.deleteById(id);
		return "Standard deleted successfully!";
	}

}
