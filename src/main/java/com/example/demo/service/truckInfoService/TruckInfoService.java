package com.example.demo.service.truckInfoService;

import com.example.demo.model.truckInfoModel.truckInfoRequestModel.TruckInfo;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface TruckInfoService {
    UniResult toWebService(TruckInfo truckInfo);
}
