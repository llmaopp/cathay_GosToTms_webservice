package com.example.demo.service.emptyInspectionDataService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest.EmptyInspectionData;
import com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataResponse.EmptyInspectionDataResponse;
import com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel.TroScreenUpdate;
import com.example.demo.service.emptyInspectionDataService.EmptyInspectionDataService;
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
public class EmptyInspectionDataServiceImpl  extends BaseUni implements EmptyInspectionDataService {
    private final static Logger logger = LoggerFactory.getLogger(EmptyInspectionDataServiceImpl.class);

    @Value("${interfaceUrl.EmptyInspectionDataUrl}")
    private String EmptyInspectionDataUrl;


    @Override
    public UniResult toWebService(EmptyInspectionData emptyInspectionData) {
        EmptyInspectionDataResponse emptyInspectionDataResponse =null;
        Error error =new Error();
        Header header =new Header();
        String emptyInspectionDataString="";
        UniResult uniResult= null;
        try {
            emptyInspectionDataString = XmlSerializeDeserializeMain.beanToXml(emptyInspectionData, TroScreenUpdate.class);
            uniResult = excutePost(EmptyInspectionDataUrl,emptyInspectionDataString, ContentType.TEXT_XML);
            if(uniResult.getCode().equals(Status.OK.getCode())){
                //相应结果转成对象
                emptyInspectionDataResponse = (EmptyInspectionDataResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(EmptyInspectionDataResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(emptyInspectionDataResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                emptyInspectionDataResponse.getHeader().setError(error);
                uniResult.setDataObject(emptyInspectionDataResponse);
            }
            logger.info("emptyInspectionDataResponse 调用第三方接口正常返回的结果"+emptyInspectionDataResponse.toString());
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(emptyInspectionData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                emptyInspectionDataResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(emptyInspectionDataResponse);

                logger.info("emptyInspectionDataResponse 调用第三方接口返回结果报错"+throwable);
                logger.info("emptyInspectionDataResponse 调用第三方接口返回报错结果"+emptyInspectionDataResponse.toString());
            }else {
                BeanUtils.copyProperties(emptyInspectionData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                emptyInspectionDataResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(emptyInspectionDataResponse);
                logger.info("emptyInspectionDataResponse 调用第三方接口返回结果报错"+throwable);
                logger.info("emptyInspectionDataResponse 调用第三方接口返回报错结果"+emptyInspectionDataResponse.toString());
            }

        }
        return uniResult;
    }
}
