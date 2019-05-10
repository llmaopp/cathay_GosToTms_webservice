package com.example.demo.service.googleProtoToJavaBean;

import com.example.demo.protoModel.Gos;
import com.google.protobuf.InvalidProtocolBufferException;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

/**
 * Created by cathay on 2019/4/24.
 */
public interface GoogleProtoToJavaBeanService {
    Boolean toJavaBean(Gos.GOSAny gosAny) throws InvalidProtocolBufferException, UnsupportedEncodingException, JAXBException;
}
