package com.example.demo.service.contextDataService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.contextDataModel.ContextDataRequestModel.ContextData;
import com.example.demo.model.contextDataModel.ContextDataResponseModel.ContextDataResponse;
import com.example.demo.service.contextDataService.ContextDataService;
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
 * Created by cathay on 2019/4/24.
 */
@Service
public class ContextDataServiceimpl extends BaseUni implements ContextDataService{
    private final static Logger logger = LoggerFactory.getLogger(ContextDataServiceimpl.class);
    @Value("${interfaceUrl.ContextDataUrl}")
    private String ContextDataUrl;
    @Override
    public UniResult toWebService(ContextData contextData) {
        ContextDataResponse contextDataResponse =null;
        Error error =new Error();
        Header header =new Header();
        String contextDataString="";
        UniResult uniResult= null;
        try {
            contextDataString = XmlSerializeDeserializeMain.beanToXml(contextData, ContextData.class);

            uniResult = excutePost(ContextDataUrl,contextDataString, ContentType.TEXT_XML);

            if(uniResult.getCode().equals(Status.OK.getCode())){

                //相应结果转成对象
                contextDataResponse = (ContextDataResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(ContextDataResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(contextDataResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                contextDataResponse.getHeader().setError(error);
                uniResult.setDataObject(contextDataResponse);
            }

        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(contextData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                contextDataResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(contextDataResponse);
            }else {
                BeanUtils.copyProperties(contextData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                contextDataResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(contextDataResponse);
            }

        }
        return uniResult;
    }
}
