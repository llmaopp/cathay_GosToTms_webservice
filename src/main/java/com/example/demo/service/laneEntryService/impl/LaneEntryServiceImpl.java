package com.example.demo.service.laneEntryService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.laneEntryModel.laneEntryRequestModel.LaneEntry;
import com.example.demo.model.laneEntryModel.laneEntryResponseModel.LaneEntryResponse;
import com.example.demo.service.laneEntryService.LaneEntryService;
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
public class LaneEntryServiceImpl extends BaseUni implements LaneEntryService {
    private final static Logger logger = LoggerFactory.getLogger(LaneEntryServiceImpl.class);

    @Value("${interfaceUrl.LaneEntryUrl}")
    private String LaneEntryUrl;
    @Override
    public UniResult toWebService(LaneEntry laneEntry) {
        LaneEntryResponse laneEntryResponse =null;

        Error error =new Error();
        Header header =new Header();
        String laneEntryString="";
        UniResult uniResult= null;
        try {
            laneEntryString = XmlSerializeDeserializeMain.beanToXml(laneEntry, LaneEntry.class);

            uniResult = excutePost(LaneEntryUrl,laneEntryString, ContentType.TEXT_XML);

            if(uniResult.getCode().equals(Status.OK.getCode())){

                //相应结果转成对象
                laneEntryResponse = (LaneEntryResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(LaneEntryResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(laneEntryResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                laneEntryResponse.getHeader().setError(error);
                uniResult.setDataObject(laneEntryResponse);
            }
            logger.info("laneEntryResponse 调用第三方接口返回结果"+laneEntryResponse.toString());

        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(laneEntry.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                laneEntryResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(laneEntryResponse);
                logger.info("laneEntryResponse 调用第三方接口返回结果报错"+throwable);
                logger.info("laneEntryResponse 调用第三方接口返回报错结果"+laneEntryResponse.toString());

            }else {
                BeanUtils.copyProperties(laneEntry.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                laneEntryResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(laneEntryResponse);
                logger.info("laneEntryResponse 调用第三方接口返回结果报错"+throwable);
                logger.info("laneEntryResponse 调用第三方接口返回报错结果"+laneEntryResponse.toString());
            }

        }
        return uniResult;
    }

}
