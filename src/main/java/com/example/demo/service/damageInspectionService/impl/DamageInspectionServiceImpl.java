package com.example.demo.service.damageInspectionService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.damageInspectionModel.damageInspectionRequest.DamageInspection;
import com.example.demo.model.damageInspectionModel.damageInspectionResponse.DamageInspectionResponse;
import com.example.demo.service.damageInspectionService.DamageInspectionService;
import com.example.demo.utils.BaseUni;
import com.example.demo.utils.Status;
import com.example.demo.utils.UniResult;
import com.example.demo.utils.XmlSerializeDeserializeMain;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

/**
 * Created by cathay on 2019/4/19.
 */

@Service
@Configuration
public class DamageInspectionServiceImpl extends BaseUni implements DamageInspectionService {
    private final static Logger logger = LoggerFactory.getLogger(DamageInspectionServiceImpl.class);


    // 第三方接口地址
    @Value("${interfaceUrl.DamageInspectionUrl}")
    private String DamageInspectionUrl;


    /**
     * 调用第三方接口
     * @param damageInspection
     * @return
     * @throws InvalidProtocolBufferException
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    @Override
    public UniResult toWebService(DamageInspection damageInspection) throws JAXBException {
        logger.info("第三方DamageInspectionUrl接口请求入参model"+damageInspection.toString());
        // 第三方返回model
        DamageInspectionResponse damageInspectionResponse = new DamageInspectionResponse();
        // 错误值model
        Error error =new Error();
        // heard 头model
        Header header =new Header();
        // 请求参数
        String damageInspectionString="";
        // 返回uniResult
        UniResult uniResult= null;
        try {
            // 将javaBean转换成xml String类型  调用转换工具类
            damageInspectionString = XmlSerializeDeserializeMain.beanToXml(damageInspection, DamageInspection.class);
            logger.info("第三方DamageInspectionUrl接口请求入参"+damageInspectionString+"地址"+DamageInspectionUrl);
            // 入参 地址，参数，类型
            uniResult = excutePost(DamageInspectionUrl,damageInspectionString, ContentType.TEXT_XML);
            if(Status.OK.equals(uniResult.getCode())){
                //相应结果转成对象
                damageInspectionResponse = (DamageInspectionResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(DamageInspectionResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(damageInspectionResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                damageInspectionResponse.getHeader().setError(error);
                uniResult.setDataObject(damageInspectionResponse);
            }
            logger.info("DamageInspectionServiceImpl调用第三方接口返回的数据"+uniResult.toString());
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(damageInspection.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                damageInspectionResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(damageInspectionResponse);
                logger.info("第三方DamageInspectionUrl接口返回报错"+throwable);
            }else {
                BeanUtils.copyProperties(damageInspection.getHeader(),header);
                error.setError_code(1011);
                error.setError_message("系统发生未知错误");
                header.setError(error);
                damageInspectionResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(damageInspectionResponse);
                logger.info("第三方DamageInspectionUrl接口返回报错"+throwable);
            }

        }
        return uniResult;
    }
}
