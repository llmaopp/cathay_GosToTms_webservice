package com.example.demo.service.OcrDataGt.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.gateDataModel.gateDataRequestModel.GateData;
import com.example.demo.model.gateDataModel.gateDataResponseModel.GateDataResponse;
import com.example.demo.service.OcrDataGt.OcrDataGtService;
import com.example.demo.utils.BaseUni;
import com.example.demo.utils.Status;
import com.example.demo.utils.UniResult;
import com.example.demo.utils.XmlSerializeDeserializeMain;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

/**
 * Created by cathay on 2019/4/28.
 */
@Service
public class OcrDataGtServiceImpl extends BaseUni implements OcrDataGtService {
    private final static Logger logger = LoggerFactory.getLogger(OcrDataGtServiceImpl.class);
    @Value("${interfaceUrl.OcrDataGtUrl}")
    private String OcrDataGtUrl;
    @Override
    public UniResult toWebService(GateData gateData) {
        GateDataResponse gateDataResponse =null;

//        TroScreenUpdate troScreenUpdate1=new TroScreenUpdate();
        Error error =new Error();
        Header header =new Header();
        String  gateDataString="";
        UniResult uniResult= null;
        try {
            gateDataString = XmlSerializeDeserializeMain.beanToXml(gateData, GateData.class);
            uniResult = excutePost(OcrDataGtUrl,gateDataString, ContentType.TEXT_XML);
            if(uniResult.getCode().equals(Status.OK.getCode())){
                //相应结果转成对象
                gateDataResponse = (GateDataResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(GateDataResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(gateDataResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                gateDataResponse.getHeader().setError(error);
                uniResult.setDataObject(gateDataResponse);
            }
            logger.info("ocrDataResponse 调用第三方接口返回结果"+gateDataResponse.toString());
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(gateData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                gateDataResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(gateDataResponse);
                logger.info("ocrDataResponse 调用第三方接口返回结果报错"+throwable);
                logger.info("ocrDataResponse 调用第三方接口返回报错结果"+gateDataResponse.toString());
            }else {
                BeanUtils.copyProperties(gateData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message("系统未知异常");
                header.setError(error);
                gateDataResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(gateDataResponse);
                logger.info("ocrDataResponse 调用第三方接口返回结果报错"+throwable);
                logger.info("ocrDataResponse 调用第三方接口返回报错结果"+gateDataResponse.toString());
            }
        }
        return uniResult;
    }
}
