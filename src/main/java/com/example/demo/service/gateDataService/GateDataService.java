package com.example.demo.service.gateDataService;

import com.example.demo.model.gateDataModel.gateDataRequestModel.GateData;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface GateDataService {
    UniResult toWebService(GateData gateData);
}
