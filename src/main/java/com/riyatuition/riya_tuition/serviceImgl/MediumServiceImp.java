package com.riyatuition.riya_tuition.serviceImgl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.entity.MediumEntity;
import com.riyatuition.riya_tuition.model.MediumModel;
import com.riyatuition.riya_tuition.repository.MediumRepo;
import com.riyatuition.riya_tuition.service.MediumService;

@Service
public class MediumServiceImp implements MediumService {

	@Autowired
	private MediumRepo mediumRepo;

	private MediumModel maptoModel(MediumEntity entity) {
		if (entity == null)
			return null;

		MediumModel model = new MediumModel();

		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setCreatedDate(entity.getCreatedDate());
		model.setModifiedDate(entity.getModifiedDate());
		return model;
	}

	@Override
	public MediumModel createMedium(MediumModel model) {

		MediumEntity entity = new MediumEntity();
		entity.setId(model.getId());
		entity.setName(model.getName());
		entity.setCreatedDate(model.getCreatedDate());
		entity.setModifiedDate(model.getModifiedDate());

		MediumEntity saved = mediumRepo.save(entity);
		return maptoModel(saved);
	}

	@Override
	public List<MediumModel> getAll() {
		return mediumRepo.findAll().stream().map(this::maptoModel).toList();
	}

	@Override
	public MediumModel getById(Integer id) {
		MediumEntity entity = mediumRepo.findById(id).orElse(null);
		return maptoModel(entity);
	}

	@Override
	public MediumModel updateMedium(Integer id, MediumModel model) {

		MediumEntity entity = mediumRepo.findById(id).orElse(null);

		if (entity == null)
			return null;

		entity.setId(model.getId());
		entity.setName(model.getName());
		entity.setModifiedDate(model.getModifiedDate());

		MediumEntity update = mediumRepo.save(entity);
		return maptoModel(update);
	}

	@Override
	public String deleteMedium(Integer id) {

		mediumRepo.deleteById(id);
		return "Standard deleted successfully!";
	}

}
