package com.example.demo.service.damageInspectionService;

import com.example.demo.model.damageInspectionModel.damageInspectionRequest.DamageInspection;
import com.example.demo.utils.UniResult;
import com.google.protobuf.InvalidProtocolBufferException;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

/**
 * Created by cathay on 2019/4/19.
 */

public interface DamageInspectionService {


    UniResult toWebService(DamageInspection damageInspection) throws InvalidProtocolBufferException, JAXBException, UnsupportedEncodingException;
}
