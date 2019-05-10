package com.example.demo.service.MoveStatusUpdateService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.moveStatusUpdateModel.moveStatusUpdateRequestModel.MoveStatusUpdate;
import com.example.demo.model.moveStatusUpdateModel.moveStatusUpdateResponeModel.MoveStatusUpdateResponse;
import com.example.demo.service.MoveStatusUpdateService.MoveStatusUpdateService;
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
public class MoveStatusUpdateServiceImpl extends BaseUni implements MoveStatusUpdateService {
    private final static Logger logger = LoggerFactory.getLogger(MoveStatusUpdateServiceImpl.class);


    @Value("${interfaceUrl.MoveStatusUpdateUrl}")
    private String MoveStatusUpdateUrl;
    @Override
    public UniResult toWebService(MoveStatusUpdate moveStatusUpdate) {
        MoveStatusUpdateResponse moveStatusUpdateResponse =null;

//        TroScreenUpdate troScreenUpdate1=new TroScreenUpdate();
        Error error =new Error();
        Header header =new Header();
        String  moveStatusUpdateString="";
        UniResult uniResult= null;
        try {
            moveStatusUpdateString = XmlSerializeDeserializeMain.beanToXml(moveStatusUpdate, MoveStatusUpdate.class);

            uniResult = excutePost(MoveStatusUpdateUrl,moveStatusUpdateString, ContentType.TEXT_XML);


            if(uniResult.getCode().equals(Status.OK.getCode())){

                //相应结果转成对象
                moveStatusUpdateResponse = (MoveStatusUpdateResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(MoveStatusUpdateResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(moveStatusUpdateResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                moveStatusUpdateResponse.getHeader().setError(error);
                uniResult.setDataObject(moveStatusUpdateResponse);
            }
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(moveStatusUpdate.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                moveStatusUpdateResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(moveStatusUpdateResponse);
            }else {
                BeanUtils.copyProperties(moveStatusUpdate.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                moveStatusUpdateResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(moveStatusUpdateResponse);
            }
        }
        return uniResult;
    }
}
