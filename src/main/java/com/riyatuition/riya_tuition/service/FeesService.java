package com.riyatuition.riya_tuition.service;

import java.math.BigDecimal;
import java.util.List;

import com.riyatuition.riya_tuition.enums.PaymentType;
import com.riyatuition.riya_tuition.model.FeesModel;

public interface FeesService {

    FeesModel createFees(FeesModel model);

    List<FeesModel> getAll(String date);

    FeesModel getById(Integer id);

    FeesModel updateFees(Integer id, FeesModel model);
    
    FeesModel settleBalance(Integer id, BigDecimal paid, PaymentType paymentType);


    String deleteFees(Integer id);
}
