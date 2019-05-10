package com.example.demo.service.emptyInspectionDataService;

import com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest.EmptyInspectionData;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface EmptyInspectionDataService {
    UniResult toWebService(EmptyInspectionData emptyInspectionData);
}
