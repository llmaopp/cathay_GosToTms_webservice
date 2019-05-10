package com.example.demo.service.laneEntryService;

import com.example.demo.model.laneEntryModel.laneEntryRequestModel.LaneEntry;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/24.
 */
public interface LaneEntryService {
    UniResult toWebService(LaneEntry laneEntry);

}
