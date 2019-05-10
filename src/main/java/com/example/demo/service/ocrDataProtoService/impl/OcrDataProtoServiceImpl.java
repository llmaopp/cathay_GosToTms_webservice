package com.example.demo.service.ocrDataProtoService.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.ocrDataModel.ocrDataRequestModel.OcrData;
import com.example.demo.model.ocrDataModel.ocrDataResponseModel.OcrDataResponse;
import com.example.demo.service.ocrDataProtoService.OcrDataProtoService;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by cathay on 2019/4/23.
 */
@Service
public class OcrDataProtoServiceImpl  extends BaseUni implements OcrDataProtoService {
    private final static Logger logger = LoggerFactory.getLogger(OcrDataProtoServiceImpl.class);

    @Value("${interfaceUrl.OcrUrl}")
    private String OcrUrl;

    @Override
    public UniResult toWebService(OcrData ocrData) {
        OcrDataResponse ocrDataResponse =null;

        Error error =new Error();
        Header header =new Header();
        String ocrDataString="";
        UniResult uniResult= null;
        try {

            // 转换成xml字符串格式
             ocrDataString = ocrDataStrings(ocrData);

            uniResult = excutePost(OcrUrl,ocrDataString, ContentType.TEXT_XML);

            if(uniResult.getCode().equals(Status.OK.getCode())){

                //相应结果转成对象
                ocrDataResponse = (OcrDataResponse) XmlSerializeDeserializeMain.convertXmlStrToObject(OcrDataResponse.class, uniResult.getDataObject().toString());
                uniResult.setDataObject(ocrDataResponse);
            }else {
                error.setError_code(Integer.parseInt(uniResult.getCode().toString()));
                error.setError_message(uniResult.getErrorDescription().toString());
                ocrDataResponse.getHeader().setError(error);
                uniResult.setDataObject(ocrDataResponse);
            }
            logger.info("ocrDataResponse 调用第三方接口返回结果"+ocrDataResponse.toString());
        } catch (Throwable throwable) {
            if(throwable instanceof JAXBException){
                BeanUtils.copyProperties(ocrData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.CODE_CONVERSIONDATA08.getCode().toString()));
                error.setError_message(Status.CODE_CONVERSIONDATA08.getDesc());
                header.setError(error);
                ocrDataResponse.setHeader(header);
                uniResult.setCode(Status.CODE_CONVERSIONDATA08.getCode());
                uniResult.setDataObject(ocrDataResponse);
                logger.info("ocrDataResponse 调用第三方接口返回结果报错"+throwable);
                logger.info("ocrDataResponse 调用第三方接口返回报错结果"+ocrDataResponse.toString());
            }else {
                BeanUtils.copyProperties(ocrData.getHeader(),header);
                error.setError_code(Integer.parseInt(Status.ERROR.getCode().toString()));
                error.setError_message(throwable.getMessage());
                header.setError(error);
                ocrDataResponse.setHeader(header);
                uniResult.setCode(Status.ERROR.getCode());
                uniResult.setDataObject(ocrDataResponse);
                logger.info("ocrDataResponse 调用第三方接口返回结果报错"+throwable);
                logger.info("ocrDataResponse 调用第三方接口返回报错结果"+ocrDataResponse.toString());
            }

        }
        return uniResult;
    }

    private String ocrDataStrings(OcrData ocrData) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=factory.newDocumentBuilder();
        Document document=builder.newDocument();

        //先添加一个根元素：root，并指定标签：languages

        Element root=document.createElement("OcrData");

        Element header=document.createElement("header");

        Element messageID=document.createElement("messageID");
        Element messageType=document.createElement("messageType");
        Element timestamp=document.createElement("timestamp");
        Element transaction=document.createElement("transaction");
        Element visitID=document.createElement("visitID");
        Element lane=document.createElement("lane");

        header.appendChild(messageID);
        header.appendChild(messageType);
        header.appendChild(timestamp);
        header.appendChild(transaction);
        header.appendChild(visitID);
        header.appendChild(lane);

        messageID.setTextContent(ocrData.getHeader().getMessageID());
        messageType.setTextContent(ocrData.getHeader().getMessageType());
        timestamp.setTextContent(ocrData.getHeader().getTimestamp());
        transaction.setTextContent(ocrData.getHeader().getTransaction());
        visitID.setTextContent(ocrData.getHeader().getVisitID());
        lane.setTextContent(ocrData.getHeader().getLane());

        Element body=document.createElement("body");

        Element passage_timestamp=document.createElement("passage_timestamp");

        passage_timestamp.setTextContent(ocrData.getBody().getPassage_timestamp());

        body.appendChild(passage_timestamp);

        int r=ocrData.getBody().getRfid_license_plate_tag().size();

        for(int rr=0;rr<r;rr++){
            Element rfid_license_plate_tag=document.createElement("rfid_license_plate_tag");
            rfid_license_plate_tag.setTextContent(ocrData.getBody().getRfid_license_plate_tag().get(rr));
            body.appendChild(rfid_license_plate_tag);
        }
        Element original_ocr_data=document.createElement("original_ocr_data");
        Element truck_license_plate=document.createElement("truck_license_plate");



        int UnitsSize=ocrData.getBody().getOriginal_ocr_data().getUnits().size();
        Element units=document.createElement("units");
        for(int y=0;y<UnitsSize;y++){
            int UnitNumberSize =ocrData.getBody().getOriginal_ocr_data().getUnits().get(y).getUnit().getUnit_number().size();
            Element unit=document.createElement("unit");
            for(int u=0;u<UnitNumberSize;u++){
                Element unit_number=document.createElement("unit_number");
                unit_number.setTextContent(ocrData.getBody().getOriginal_ocr_data().getUnits().get(y).getUnit().getUnit_number().get(u));
                unit.appendChild(unit_number);
            }
            Element iso_code=document.createElement("iso_code");
            Element position=document.createElement("position");
            Element door_direction=document.createElement("door_direction");
            Element clip_on=document.createElement("clip_on");
            Element tank_rail=document.createElement("tank_rail");
            Element seal_presence=document.createElement("seal_presence");
            unit.appendChild(iso_code);
            unit.appendChild(position);
            unit.appendChild(door_direction);
            unit.appendChild(clip_on);
            unit.appendChild(tank_rail);
            unit.appendChild(seal_presence);
            units.appendChild(unit);
        }






        truck_license_plate.setTextContent(ocrData.getBody().getOriginal_ocr_data().getTruck_license_plate());

        original_ocr_data.appendChild(truck_license_plate);
        original_ocr_data.appendChild(units);

        body.appendChild(original_ocr_data);



        Element updated_ocr_data=document.createElement("updated_ocr_data");
        Element updated_truck_license_plate=document.createElement("truck_license_plate");


        Element updated_units=document.createElement("units");
        int updated_unit_Size=ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().size();
        for(int t=0;t<updated_unit_Size;t++){

            Element  updated_unit=document.createElement("unit");

            int unit_numberSize=ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getUnitNumberList().size();

            for (int q=0;q<unit_numberSize;q++){
                Element unit_number=document.createElement("unit_number");
                unit_number.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getUnitNumberList().get(q).getUnit_number());
                unit_number.setAttribute("bundle_maste",ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getUnitNumberList().get(q).getBundle_master());
                updated_unit.appendChild(unit_number);
            }



            Element updated_iso_code=document.createElement("iso_code");
            Element updated_position=document.createElement("position");
            Element updated_door_direction=document.createElement("door_direction");
            Element updated_clip_on=document.createElement("clip_on");
            Element updated_tank_rail=document.createElement("tank_rail");
            Element updated_seal_presence=document.createElement("seal_presence");
            Element updated_e_seal_state=document.createElement("e_seal_state");
            Element updated_overheight =document.createElement("overheight");
            Element updated_dg_presence=document.createElement("dg_presence");
            Element updated_flatrack_header_down=document.createElement("flatrack_header_down");

            updated_unit.appendChild(updated_iso_code);
            updated_unit.appendChild(updated_position);
            updated_unit.appendChild(updated_door_direction);
            updated_unit.appendChild(updated_clip_on);
            updated_unit.appendChild(updated_tank_rail);
            updated_unit.appendChild(updated_seal_presence);
            updated_unit.appendChild(updated_e_seal_state);
            updated_unit.appendChild(updated_overheight);
            updated_unit.appendChild(updated_dg_presence);
            int dg_classSize =ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getDg_class().size();
            for(int e=0;e<dg_classSize;e++){
                Element updated_dg_class=document.createElement("dg_class");
                updated_dg_class.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getDg_class().get(e));
                updated_unit.appendChild(updated_dg_class);
            }

            updated_unit.appendChild(updated_flatrack_header_down);
            updated_units.appendChild(updated_unit);

            updated_iso_code.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getIso_code());
            updated_position.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getPosition());
            updated_door_direction.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getDoor_direction());
            updated_clip_on.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getClip_on());
            updated_tank_rail.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getTank_rail());
            updated_seal_presence.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getSeal_presence());
            updated_e_seal_state.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getE_seal_state());
            updated_overheight.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getOverheight());
            updated_dg_presence.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getDg_presence());
            updated_flatrack_header_down.setTextContent(ocrData.getBody().getUpdated_ocr_data().getUnits().getUnit().get(t).getFlatrack_header_down());


        }
        updated_ocr_data.appendChild(updated_units);

        updated_ocr_data.appendChild(updated_truck_license_plate);


        body.appendChild(updated_ocr_data);
        Element operator_id=document.createElement("operator_id");
        Element snapshot_location=document.createElement("snapshot_location");
        operator_id.setTextContent(ocrData.getBody().getOperator_id());
        snapshot_location.setTextContent(ocrData.getBody().getSnapshot_location());
        body.appendChild(operator_id);

        body.appendChild(snapshot_location);

        root.appendChild(header);
        root.appendChild(body);

        document.appendChild(root);

        TransformerFactory transformerFactory= TransformerFactory.newInstance();

        Transformer transformer=transformerFactory.newTransformer();
        StringWriter writer=new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));

        String e=writer.toString();
        return e;
    }
}
