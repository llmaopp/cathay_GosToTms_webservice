package com.example.demo.service.truckInfoService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.truckInfoModel.truckInfoRequestModel.TruckInfo;
import com.example.demo.model.truckInfoModel.truckInfoResponeModel.TruckInfoResponse;
import com.example.demo.service.truckInfoService.TruckInfoService;
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
public class TruckInfoServiceImpl  extends BaseUni implements TruckInfoService {
    private final static Logger logger = LoggerFactory.getLogger(TruckInfoServiceImpl.class);
    @Value("${interfaceUrl.TruckInfoUrl}")
    private String TruckInfoUrl;
    @Override
    public UniResult toWebService(TruckInfo truckInfo) {
        TruckInfoResponse truckInfoResponse =null;

//        TroScreenUpdate troScreenUpdate1=new TroScreenUpdate();
        Error error =new Error();
        Header header =new Header();
        String truckInfoString="";
        UniResult uniResult= null;
        try {
            truckInfoString = XmlSerializeDeserializeMain.beanToXml(truckInfo, TruckInfo.class);
            uniResult = excutePost(TruckInfoUrl,truckInfoString, ContentType.TEXT_XML);
            if(uniResult.getCode().equals(Status.OK.getCode())){
                //相应结果转成对象
                truckInfoResponse = (TruckInfoResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(TruckInfoResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(truckInfoResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                truckInfoResponse.getHeader().setError(error);
                uniResult.setDataObject(truckInfoResponse);
            }
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(truckInfo.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                truckInfoResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(truckInfoResponse);
            }else {
                BeanUtils.copyProperties(truckInfo.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                truckInfoResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(truckInfoResponse);
            }
        }
        return uniResult;
    }
}
