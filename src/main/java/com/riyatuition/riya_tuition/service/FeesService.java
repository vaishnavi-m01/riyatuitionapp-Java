package com.riyatuition.riya_tuition.service;

import java.util.List;
import com.riyatuition.riya_tuition.model.FeesModel;

public interface FeesService {
    FeesModel createFees(FeesModel model);

    List<FeesModel> getAllFees();

    FeesModel getFeesById(Integer id);

    FeesModel updateFees(Integer id, FeesModel model);

    String deleteFees(Integer id);

    FeesModel settleBalance(FeesModel model);

    List<FeesModel> getRecentTransactions(Integer studentId);
}
