package com.example.demo.service.truckTriggerService.impl;

import com.example.demo.model.TruckTriggerModel.TruckTriggerRequestModel.TruckTriggerModel;
import com.example.demo.model.TruckTriggerModel.TruckTriggerResponseModel.TruckTriggerResponse;
import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel.TroScreenUpdate;
import com.example.demo.service.truckTriggerService.TruckTriggerService;
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
public class TruckTriggerServiceImpl extends BaseUni implements TruckTriggerService {
    private final static Logger logger = LoggerFactory.getLogger(TruckTriggerServiceImpl.class);
    @Value("${interfaceUrl.TruckTriggerUrl}")
    private String TruckTriggerUrl;
    @Override
    public UniResult toWebService(TruckTriggerModel truckTriggerModel) {
        TruckTriggerResponse truckTriggerResponse =null;

//        TroScreenUpdate troScreenUpdate1=new TroScreenUpdate();
        Error error =new Error();
        Header header =new Header();
        String  truckTriggerString="";
        UniResult uniResult= null;
        try {
            truckTriggerString = XmlSerializeDeserializeMain.beanToXml(truckTriggerModel, TroScreenUpdate.class);

            uniResult = excutePost(TruckTriggerUrl,truckTriggerString, ContentType.TEXT_XML);


            if(uniResult.getCode().equals(Status.OK.getCode())){

                //相应结果转成对象
                truckTriggerResponse = (TruckTriggerResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(TruckTriggerResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(truckTriggerResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                truckTriggerResponse.getHeader().setError(error);
                uniResult.setDataObject(truckTriggerResponse);
            }
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(truckTriggerModel.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                truckTriggerResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(truckTriggerResponse);
            }else {
                BeanUtils.copyProperties(truckTriggerModel.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                truckTriggerResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(truckTriggerResponse);
            }
        }
        return uniResult;
    }
}
