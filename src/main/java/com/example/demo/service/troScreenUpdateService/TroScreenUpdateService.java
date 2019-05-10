package com.example.demo.service.troScreenUpdateService;

import com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel.TroScreenUpdate;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface TroScreenUpdateService {
    UniResult toWebService(TroScreenUpdate troScreenUpdate);
}
