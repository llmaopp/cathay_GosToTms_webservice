package com.example.demo.service.MoveStatusUpdateService;

import com.example.demo.model.moveStatusUpdateModel.moveStatusUpdateRequestModel.MoveStatusUpdate;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface MoveStatusUpdateService {
    UniResult toWebService(MoveStatusUpdate moveStatusUpdate);
}
