package com.example.demo.service.troScreenUpdateService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel.TroScreenUpdate;
import com.example.demo.model.troScreenUpdateModel.troScreenUpdateResponseModel.TroScreenUpdateResponse;
import com.example.demo.service.troScreenUpdateService.TroScreenUpdateService;
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
public class TroScreenUpdateServiceImpl  extends BaseUni implements TroScreenUpdateService {
    private final static Logger logger = LoggerFactory.getLogger(TroScreenUpdateServiceImpl.class);


    @Value("${interfaceUrl.TroScreenUpdateUrl}")
    private String TroScreenUpdateUrl;

    @Override
    public UniResult toWebService(TroScreenUpdate troScreenUpdate) {
        TroScreenUpdateResponse troScreenUpdateResponse =null;

//        TroScreenUpdate troScreenUpdate1=new TroScreenUpdate();
        Error error =new Error();
        Header header =new Header();
        String troScreenUpdateString="";
        UniResult uniResult= null;
        try {
            troScreenUpdateString = XmlSerializeDeserializeMain.beanToXml(troScreenUpdate, TroScreenUpdate.class);

            uniResult = excutePost(TroScreenUpdateUrl,troScreenUpdateString, ContentType.TEXT_XML);


            if(uniResult.getCode().equals(Status.OK.getCode())){

                //相应结果转成对象
                troScreenUpdateResponse = (TroScreenUpdateResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(TroScreenUpdateResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(troScreenUpdateResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                troScreenUpdateResponse.getHeader().setError(error);
                uniResult.setDataObject(troScreenUpdateResponse);
            }
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(troScreenUpdate.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                troScreenUpdateResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(troScreenUpdateResponse);
            }else {
                BeanUtils.copyProperties(troScreenUpdate.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                troScreenUpdateResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(troScreenUpdateResponse);
            }

        }
        return uniResult;
    }
}
