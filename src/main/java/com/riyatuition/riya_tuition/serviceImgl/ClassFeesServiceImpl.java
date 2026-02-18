package com.riyatuition.riya_tuition.serviceImgl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.entity.ClassFeesEntity;
import com.riyatuition.riya_tuition.entity.StandardEntity;
import com.riyatuition.riya_tuition.model.ClassFeesModel;
import com.riyatuition.riya_tuition.repository.ClassFeesRepository;
import com.riyatuition.riya_tuition.repository.StandardRepo;
import com.riyatuition.riya_tuition.service.ClassFeesService;

@Service

public class ClassFeesServiceImpl implements ClassFeesService {
	@Autowired
	private ClassFeesRepository classFeesRepo;

	@Autowired
	private StandardRepo standardRepo;

	private ClassFeesModel mapToModel(ClassFeesEntity entity) {

		if (entity == null)
			return null;
		ClassFeesModel model = new ClassFeesModel();

		model.setId(entity.getId());

		if (entity.getStandard() != null) {
			model.setStandardId(entity.getStandard().getId());
			model.setStandardName(entity.getStandard().getName());

		}
		model.setAmount(entity.getAmount());
		model.setCreatedDate(entity.getCreatedDate());
		model.setModifiedDate(entity.getModifiedDate());
		return model;

	}

	@Override
	public ClassFeesModel createClassFees(ClassFeesModel model) {

		StandardEntity standard = standardRepo.findById(model.getStandardId())
				.orElseThrow(() -> new RuntimeException("Standard not found"));

		ClassFeesEntity entity = new ClassFeesEntity();

		entity.setAmount(model.getAmount());
		entity.setCreatedDate(model.getCreatedDate());
		entity.setModifiedDate(model.getModifiedDate());

		entity.setStandard(standard);

		ClassFeesEntity savedClassFees = classFeesRepo.save(entity);

		return mapToModel(savedClassFees);
	}

	@Override
	public List<ClassFeesModel> getAllClassFees() {
		return classFeesRepo.findAll().stream().map(this::mapToModel).toList();
	}

	@Override
	public ClassFeesModel getById(Integer id) {
		ClassFeesEntity entity = classFeesRepo.findById(id).orElse(null);
		return mapToModel(entity);
	}

	@Override
	public ClassFeesModel getByStandardId(Integer standardId) {

		ClassFeesEntity entity = classFeesRepo
				.findByStandardId(standardId)
				.orElse(null);

		return mapToModel(entity);
	}

	@Override
	public ClassFeesModel getByIdAndStandardId(Integer id, Integer standardId) {
		ClassFeesEntity entity = classFeesRepo.findByIdAndStandardId(id, standardId)
				.orElse(null);
		return mapToModel(entity);
	}

	@Override
	public ClassFeesModel updateClassFees(Integer id, ClassFeesModel model) {
		ClassFeesEntity entity = classFeesRepo.findById(id).orElse(null);

		if (entity == null) {
			return null;
		}

		entity.setId(model.getId());
		entity.setAmount(model.getAmount());
		StandardEntity standard = standardRepo.findById(model.getStandardId()).orElse(null);
		entity.setStandard(standard);
		entity.setModifiedDate(model.getModifiedDate());

		ClassFeesEntity update = classFeesRepo.save(entity);

		return mapToModel(update);
	}

	@Override
	public String deleteClassFees(Integer id) {
		classFeesRepo.deleteById(id);
		return "Class Fees Deleted Successfully";
	}

}
