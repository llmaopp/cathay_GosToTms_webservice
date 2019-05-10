package com.example.demo.service.OcrDataGt;

import com.example.demo.model.gateDataModel.gateDataRequestModel.GateData;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/28.
 */
public interface OcrDataGtService {
    UniResult toWebService(GateData gateData);
}
