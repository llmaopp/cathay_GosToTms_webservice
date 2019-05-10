package com.example.demo.service.transferDataService;

import com.example.demo.model.transferDataModel.transferDataRequestModel.TransferData;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface TransferDataService {
    UniResult toWebService(TransferData transferData);
}
