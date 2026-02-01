package com.riyatuition.riya_tuition.service;

import java.util.List;

import com.riyatuition.riya_tuition.model.ClassFeesModel;


public interface ClassFeesService {

    ClassFeesModel createClassFees(ClassFeesModel model);

    List<ClassFeesModel> getAllClassFees();

    ClassFeesModel getById(Integer id);

    ClassFeesModel getByStandardId(Integer standardId);
    
    ClassFeesModel getByIdAndStandardId(Integer id, Integer standardId);

    ClassFeesModel updateClassFees(Integer id, ClassFeesModel model);

    String deleteClassFees(Integer id);
}
