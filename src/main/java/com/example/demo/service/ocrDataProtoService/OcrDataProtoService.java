package com.example.demo.service.ocrDataProtoService;

import com.example.demo.model.ocrDataModel.ocrDataRequestModel.OcrData;
import com.example.demo.utils.UniResult;

/**
 * Created by cathay on 2019/4/23.
 */
public interface OcrDataProtoService {



   UniResult toWebService(OcrData ocrData);

}
