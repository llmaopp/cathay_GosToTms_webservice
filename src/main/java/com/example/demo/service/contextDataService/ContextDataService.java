package com.example.demo.service.contextDataService;

import com.example.demo.model.contextDataModel.ContextDataRequestModel.ContextData;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface ContextDataService {
    UniResult toWebService(ContextData contextData);
}
