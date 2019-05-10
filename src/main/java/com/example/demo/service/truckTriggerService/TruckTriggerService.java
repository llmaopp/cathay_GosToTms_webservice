package com.example.demo.service.truckTriggerService;

import com.example.demo.model.TruckTriggerModel.TruckTriggerRequestModel.TruckTriggerModel;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface TruckTriggerService {
    UniResult toWebService(TruckTriggerModel truckTriggerModel);
}
