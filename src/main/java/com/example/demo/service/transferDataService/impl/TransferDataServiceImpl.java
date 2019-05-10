package com.example.demo.service.transferDataService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.transferDataModel.transferDataRequestModel.TransferData;
import com.example.demo.model.transferDataModel.transferDataResponseModel.TransferDataResponse;
import com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel.TroScreenUpdate;
import com.example.demo.service.transferDataService.TransferDataService;
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
public class TransferDataServiceImpl extends BaseUni implements TransferDataService{
    private final static Logger logger = LoggerFactory.getLogger(TransferDataServiceImpl.class);

    @Value("${interfaceUrl.transferDataUrl}")
    private String transferDataUrl;
    @Override
    public UniResult toWebService(TransferData transferData) {
        TransferDataResponse transferDataResponse =null;

//        TroScreenUpdate troScreenUpdate1=new TroScreenUpdate();
        Error error =new Error();
        Header header =new Header();
        String transferDataString="";
        UniResult uniResult= null;
        try {
            transferDataString = XmlSerializeDeserializeMain.beanToXml(transferData, TroScreenUpdate.class);

            uniResult = excutePost(transferDataUrl,transferDataString, ContentType.TEXT_XML);


            if(uniResult.getCode().equals(Status.OK.getCode())){

                //相应结果转成对象
                transferDataResponse = (TransferDataResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(TransferDataResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(transferDataResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                transferDataResponse.getHeader().setError(error);
                uniResult.setDataObject(transferDataResponse);
            }
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(transferData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                transferDataResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(transferDataResponse);
            }else {
                BeanUtils.copyProperties(transferData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                transferDataResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(transferDataResponse);
            }

        }
        return uniResult;
    }
}
