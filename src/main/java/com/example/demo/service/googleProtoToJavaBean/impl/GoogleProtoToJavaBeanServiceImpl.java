package com.example.demo.service.googleProtoToJavaBean.impl;

import com.example.demo.model.commodel.Error;
import com.example.demo.model.commodel.Header;
import com.example.demo.model.damageInspectionModel.damageInspectionRequest.DamageInspection;
import com.example.demo.model.damageInspectionModel.damageInspectionRequest.DamageInspectionModel;
import com.example.demo.model.damageInspectionModel.damageInspectionRequest.UntisTwo;
import com.example.demo.model.damageInspectionModel.damageInspectionRequest.Untisone;
import com.example.demo.model.damageInspectionModel.damageInspectionResponse.DamageInspectionResponse;
import com.example.demo.model.gateDataModel.gateDataRequestModel.*;
import com.example.demo.model.gateDataModel.gateDataResponseModel.GateDataResponse;
import com.example.demo.model.laneEntryModel.laneEntryRequestModel.LaneEntry;
import com.example.demo.model.laneEntryModel.laneEntryRequestModel.LaneEntryManualCheck;
import com.example.demo.model.laneEntryModel.laneEntryRequestModel.LaneEntryModel;
import com.example.demo.model.laneEntryModel.laneEntryRequestModel.LaneEntryScreenMessage;
import com.example.demo.model.laneEntryModel.laneEntryResponseModel.LaneEntryResponse;
import com.example.demo.model.ocrDataModel.ocrDataRequestModel.*;
import com.example.demo.model.ocrDataModel.ocrDataResponseModel.OcrDataResponse;
import com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel.TroScreenUpdate;
import com.example.demo.protoModel.Gos;
import com.example.demo.service.OcrDataGt.OcrDataGtService;
import com.example.demo.service.ZmqClient;
import com.example.demo.service.damageInspectionService.DamageInspectionService;
import com.example.demo.service.gateDataService.GateDataService;
import com.example.demo.service.googleProtoToJavaBean.GoogleProtoToJavaBeanService;
import com.example.demo.service.laneEntryService.LaneEntryService;
import com.example.demo.service.ocrDataProtoService.OcrDataProtoService;
import com.example.demo.utils.Status;
import com.example.demo.utils.UniResult;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@Service
@Configuration
public class GoogleProtoToJavaBeanServiceImpl implements GoogleProtoToJavaBeanService {
    private final static Logger logger = LoggerFactory.getLogger(GoogleProtoToJavaBeanServiceImpl.class);

    @Autowired
    private DamageInspectionService damageInspectionService;

    @Autowired
    private OcrDataProtoService ocrDataProtoService;
    @Autowired
    private LaneEntryService laneEntryService;

    @Autowired
    private GateDataService gateDataService;
    @Autowired
    private OcrDataGtService ocrDataGtService;

    @Autowired
    private ZmqClient zmqClient;
    @Override
    public Boolean toJavaBean(Gos.GOSAny gosAny) throws UnsupportedEncodingException, JAXBException, InvalidProtocolBufferException {
        boolean t=false;

        Gos.GOSAny gosAny1 =null;
        try {


        Boolean tf=false;

        if("type.googleapis.com/gos.pb.DamageInspection".equals(gosAny.getAny().getTypeUrl())){
            tf=true;
            logger.info("DamageInspection 符合" +gosAny);
            // zmq 发过来的消息转换成JavaBean
            DamageInspection damageInspection=  toDamageInspection(gosAny);
            logger.info("转换成Java bean  " +damageInspection);
            // 请求第三方接口 返回后转换成 zmq发送的详细
             gosAny1= toDamageInspectionResponse(damageInspection,gosAny);
            logger.info("DamageInspection与第一方闸口进行zmq通信"+gosAny1.getAny().toString());
            throw new UnsupportedEncodingException("未添加资产,请重新添加");
        }else if ("type.googleapis.com/gos.pb.OcrData".equals(gosAny.getAny().getTypeUrl())){
            tf=true;
            logger.info("OcrData 符合："+gosAny);
            OcrData ocrData=  toOcrData(gosAny);
            logger.info("转换成Java bean  " +ocrData);
            gosAny1= toOcrDataResponse(ocrData,gosAny);
            logger.info("OcrData与第一方闸口进行zmq通信"+gosAny1);
        }else if ("type.googleapis.com/gos.pb.OcrDataGT".equals(gosAny.getAny().getTypeUrl())){
            tf=true;
            logger.info("OcrDataGT 符合："+gosAny);
            GateData gateData=  toOcrDataGt(gosAny);
            gosAny1= toOcrDataGtResponse(gateData,gosAny);
            logger.info("OcrDataGT与第一方闸口进行zmq通信"+gosAny1);
        } else if ("type.googleapis.com/gos.pb.LaneEntry".equals(gosAny.getAny().getTypeUrl())){
            tf=true;
            logger.info("LaneEntry 符合："+gosAny);
            LaneEntry laneEntry=  tolaneEntry(gosAny);
            gosAny1= toLaneEntryResponse(laneEntry,gosAny);
            logger.info("LaneEntry与第一方闸口进行zmq通信"+gosAny1);
        }else if ("type.googleapis.com/gos.pb.GateData".equals(gosAny.getAny().getTypeUrl())){
            tf=true;
            logger.info("GateData 符合："+gosAny);
            GateData gateData=  toGateData(gosAny);
            gosAny1= toGateDataResponse(gateData,gosAny);
            logger.info("GateData与第一方闸口进行zmq通信"+gosAny1);
        }
            if (tf) {
                zmqClient.send(gosAny1);
                t=true;
                logger.info("与第一方闸口进行zmq通信"+gosAny1);
            }else {
                logger.info("收到消息不对，不属于处理"+gosAny);
                t=true;
            }
        }catch (Throwable throwable){
            if (throwable instanceof InvalidProtocolBufferException){
                logger.info("转换成google proto 与javaBean转换异常 格式异常"+throwable);
                Gos.GOSAny gosAny3=sendProTo(gosAny,Status.CODE_CONVERSIONDATA09);
                zmqClient.send(gosAny3);
                t=true;
            }else if (throwable instanceof UnsupportedEncodingException){
                logger.info("转换成google proto 与javaBean转换异常 格式异常"+throwable);
                Gos.GOSAny gosAny3=sendProTo(gosAny,Status.CODE_CONVERSIONDATA09);
                zmqClient.send(gosAny3);
                t=true;
            }else  if (throwable instanceof JAXBException){
                logger.info("转换成avaBean转换xml 调用第三方几口之前发生异常"+throwable);
                Gos.GOSAny gosAny3=sendProTo(gosAny,Status.CODE_CONVERSIONDATA10);
                zmqClient.send(gosAny3);
                t=true;
            }else {
                logger.info("系统发生未知异常"+throwable);
                Gos.GOSAny gosAny3=sendProTo(gosAny,Status.CODE_CONVERSIONDATA11);
                zmqClient.send(gosAny3);
                t=true;
            }
        }
        return t;
    }

    private Gos.GOSAny sendProTo(Gos.GOSAny gosAny,Status status) throws InvalidProtocolBufferException, UnsupportedEncodingException {
        Gos.GOSAny gosAny1 =null;
        if("type.googleapis.com/gos.pb.DamageInspection".equals(gosAny.getAny().getTypeUrl())){
            Gos.DamageInspection damageInspection=gosAny.getAny().unpack(Gos.DamageInspection.class);

            Gos.Error.Builder error =Gos.Error.newBuilder();
            error.setErrorCode(status.getCode().hashCode());
            String errorMage=status.getDesc();
            error.setErrorMessage(ByteString.copyFrom(errorMage.getBytes("gbk")));

            Gos.Header.Builder headerBuilder=Gos.Header.newBuilder();
            headerBuilder.setLane(damageInspection.getHeader().getLane());
            headerBuilder.setLane(damageInspection.getHeader().getLane());
            headerBuilder.setMessageType(damageInspection.getHeader().getMessageType());
            headerBuilder.setVisitID(damageInspection.getHeader().getMessageID());
            headerBuilder.setTimestamp(damageInspection.getHeader().getTimestamp());
            headerBuilder.setTransaction(damageInspection.getHeader().getTransaction());
            headerBuilder.addErrArr(error);

            Gos.DamageInspection.Builder damageInspectionBuilder=Gos.DamageInspection.newBuilder();
            damageInspectionBuilder.setHeader(headerBuilder);
            damageInspectionBuilder.setBody(damageInspection.getBody());
            gosAny1 =Gos.GOSAny.newBuilder().setMsgType(gosAny.getMsgType()).setVisitId(gosAny.getVisitId()).setModuleName(gosAny.getModuleName())
                    .setID(1)
                    .setAny(Any.pack(damageInspectionBuilder.build()))
                    .build();


        }else if ("type.googleapis.com/gos.pb.OcrData".equals(gosAny.getAny().getTypeUrl())){
            Gos.OcrData ocrData=gosAny.getAny().unpack(Gos.OcrData.class);

            Gos.Error.Builder error =Gos.Error.newBuilder();
            error.setErrorCode(status.getCode().hashCode());
            String errorMage=status.getDesc();
            error.setErrorMessage(ByteString.copyFrom(errorMage.getBytes("gbk")));

            Gos.Header.Builder headerBuilder=Gos.Header.newBuilder();
            headerBuilder.setLane(ocrData.getHeader().getLane());
            headerBuilder.setLane(ocrData.getHeader().getLane());
            headerBuilder.setMessageType(ocrData.getHeader().getMessageType());
            headerBuilder.setVisitID(ocrData.getHeader().getMessageID());
            headerBuilder.setTimestamp(ocrData.getHeader().getTimestamp());
            headerBuilder.setTransaction(ocrData.getHeader().getTransaction());
            headerBuilder.addErrArr(error);

            Gos.OcrData.Builder ocrDataBuilder=Gos.OcrData.newBuilder();
            ocrDataBuilder.setHeader(headerBuilder);
            ocrDataBuilder.setBody(ocrData.getBody());
            gosAny1 =Gos.GOSAny.newBuilder().setMsgType(gosAny.getMsgType()).setVisitId(gosAny.getVisitId()).setModuleName(gosAny.getModuleName())
                    .setID(1)
                    .setAny(Any.pack(ocrDataBuilder.build()))
                    .build();


        }else if ("type.googleapis.com/gos.pb.OcrDataGT".equals(gosAny.getAny().getTypeUrl())){
            Gos.OcrDataGT ocrDataGT=gosAny.getAny().unpack(Gos.OcrDataGT.class);

            Gos.Error.Builder error =Gos.Error.newBuilder();
            error.setErrorCode(status.getCode().hashCode());
            String errorMage=status.getDesc();
            error.setErrorMessage(ByteString.copyFrom(errorMage.getBytes("gbk")));

            Gos.Header.Builder headerBuilder=Gos.Header.newBuilder();
            headerBuilder.setLane(ocrDataGT.getHeader().getLane());
            headerBuilder.setLane(ocrDataGT.getHeader().getLane());
            headerBuilder.setMessageType(ocrDataGT.getHeader().getMessageType());
            headerBuilder.setVisitID(ocrDataGT.getHeader().getMessageID());
            headerBuilder.setTimestamp(ocrDataGT.getHeader().getTimestamp());
            headerBuilder.setTransaction(ocrDataGT.getHeader().getTransaction());
            headerBuilder.addErrArr(error);

            Gos.OcrDataGT.Builder ocrDataGtBuilder=Gos.OcrDataGT.newBuilder();
            ocrDataGtBuilder.setHeader(headerBuilder);
            ocrDataGtBuilder.setBody(ocrDataGT.getBody());
            gosAny1 =Gos.GOSAny.newBuilder().setMsgType(gosAny.getMsgType()).setVisitId(gosAny.getVisitId()).setModuleName(gosAny.getModuleName())
                    .setID(1)
                    .setAny(Any.pack(ocrDataGtBuilder.build()))
                    .build();



        } else if ("type.googleapis.com/gos.pb.LaneEntry".equals(gosAny.getAny().getTypeUrl())){
            Gos.LaneEntry laneEntry=gosAny.getAny().unpack(Gos.LaneEntry.class);

            Gos.Error.Builder error =Gos.Error.newBuilder();
            error.setErrorCode(status.getCode().hashCode());
            String errorMage=status.getDesc();
            error.setErrorMessage(ByteString.copyFrom(errorMage.getBytes("gbk")));

            Gos.Header.Builder headerBuilder=Gos.Header.newBuilder();
            headerBuilder.setLane(laneEntry.getHeader().getLane());
            headerBuilder.setLane(laneEntry.getHeader().getLane());
            headerBuilder.setMessageType(laneEntry.getHeader().getMessageType());
            headerBuilder.setVisitID(laneEntry.getHeader().getMessageID());
            headerBuilder.setTimestamp(laneEntry.getHeader().getTimestamp());
            headerBuilder.setTransaction(laneEntry.getHeader().getTransaction());
            headerBuilder.addErrArr(error);

            Gos.LaneEntry.Builder laneEntryBuilder=Gos.LaneEntry.newBuilder();
            laneEntryBuilder.setHeader(headerBuilder);
            laneEntryBuilder.setBody(laneEntry.getBody());
            gosAny1 =Gos.GOSAny.newBuilder().setMsgType(gosAny.getMsgType()).setVisitId(gosAny.getVisitId()).setModuleName(gosAny.getModuleName())
                    .setID(1)
                    .setAny(Any.pack(laneEntryBuilder.build()))
                    .build();




        }else if ("type.googleapis.com/gos.pb.GateData".equals(gosAny.getAny().getTypeUrl())){
            Gos.GateData gateData=gosAny.getAny().unpack(Gos.GateData.class);

            Gos.Error.Builder error =Gos.Error.newBuilder();
            error.setErrorCode(status.getCode().hashCode());
            String errorMage=status.getDesc();
            error.setErrorMessage(ByteString.copyFrom(errorMage.getBytes("gbk")));

            Gos.Header.Builder headerBuilder=Gos.Header.newBuilder();
            headerBuilder.setLane(gateData.getHeader().getLane());
            headerBuilder.setLane(gateData.getHeader().getLane());
            headerBuilder.setMessageType(gateData.getHeader().getMessageType());
            headerBuilder.setVisitID(gateData.getHeader().getMessageID());
            headerBuilder.setTimestamp(gateData.getHeader().getTimestamp());
            headerBuilder.setTransaction(gateData.getHeader().getTransaction());
            headerBuilder.addErrArr(error);

            Gos.GateData.Builder gateDataBuilder=Gos.GateData.newBuilder();
            gateDataBuilder.setHeader(headerBuilder);
            gateDataBuilder.setBody(gateData.getBody());
            gosAny1 =Gos.GOSAny.newBuilder().setMsgType(gosAny.getMsgType()).setVisitId(gosAny.getVisitId()).setModuleName(gosAny.getModuleName())
                    .setID(1)
                    .setAny(Any.pack(gateDataBuilder.build()))
                    .build();



        }
        return gosAny1;
    }

    private Gos.GOSAny toOcrDataGtResponse(GateData gateData, Gos.GOSAny gosAny1) throws UnsupportedEncodingException {
        GateDataResponse ocrDataGtResponse= null;

        logger.info("调用DamageInspection第三方接口");
        UniResult u= ocrDataGtService.toWebService(gateData);

        if (Status.OK.equals(u.getCode()) ){

            ocrDataGtResponse= (GateDataResponse) u.getDataObject();

        }else {
            ocrDataGtResponse= (GateDataResponse) u.getDataObject();

        }
        Gos.OcrDataGT.Builder GateData= Gos.OcrDataGT.newBuilder();

        Gos.Header.Builder header =Gos.Header.newBuilder();

        Gos.Error.Builder errorder= Gos.Error.newBuilder();

        Gos.GateDataBody.Builder GateDataBody= Gos.GateDataBody.newBuilder();

        Gos.UnitOcrData.Builder unitOcrData= Gos.UnitOcrData.newBuilder();


        Gos.ScreenMessage.Builder screenMessage= Gos.ScreenMessage.newBuilder();




        if(ocrDataGtResponse!=null){
            if(ocrDataGtResponse.getHeader()!=null){
                if(ocrDataGtResponse.getHeader().getMessageType()!=null){header.setMessageType(ByteString.copyFrom(ocrDataGtResponse.getHeader().getMessageType().getBytes("gbk")));}
                if(ocrDataGtResponse.getHeader().getMessageID()!=null){header.setMessageID(ByteString.copyFrom(ocrDataGtResponse.getHeader().getMessageID().getBytes("gbk")));}
                if(ocrDataGtResponse.getHeader().getTimestamp()!=null){header.setTimestamp(ByteString.copyFrom(ocrDataGtResponse.getHeader().getTimestamp().getBytes("gbk")));}
                if(ocrDataGtResponse.getHeader().getTransaction()!=null){header.setTransaction(ByteString.copyFrom(ocrDataGtResponse.getHeader().getTransaction().getBytes("gbk")));}
                if(ocrDataGtResponse.getHeader().getVisitID()!=null){header.setVisitID(ByteString.copyFrom(ocrDataGtResponse.getHeader().getVisitID().getBytes("gbk")));}
                if(ocrDataGtResponse.getHeader().getLane()!=null){header.setLane(ByteString.copyFrom(ocrDataGtResponse.getHeader().getLane().getBytes("gbk")));}
                if(ocrDataGtResponse.getHeader().getError()!=null){
                    if(ocrDataGtResponse.getHeader().getError().getError_code()!=0){errorder.setErrorCode(ocrDataGtResponse.getHeader().getError().getError_code());}
                    if(ocrDataGtResponse.getHeader().getError().getError_message()!=null){errorder.setErrorMessage(ByteString.copyFrom(ocrDataGtResponse.getHeader().getError().getError_message().getBytes("gbk")));}

                }
            }


            header.addErrArr(errorder);
            GateData.setHeader(header);
            if (ocrDataGtResponse.getBody()!=null){


                if(ocrDataGtResponse.getBody().getIsManual()!=null){
                    GateData.setIsManual(ByteString.copyFrom(ocrDataGtResponse.getBody().getTruck_license_plate().getBytes("gbk")));
                }

                if (ocrDataGtResponse.getBody().getRfid_license_plate_tag()!=null){

                    int y=ocrDataGtResponse.getBody().getRfid_license_plate_tag().size();
                    StringBuilder sb = new StringBuilder();
                    for(int r=0;r<y;r++){
                        String e= ocrDataGtResponse.getBody().getRfid_license_plate_tag().get(r);
                        if(e.length()>0){
                            sb.append(",");
                        }
                        sb.append(e);
                    }

                    GateDataBody.setRfidLicensePlateTag(ByteString.copyFrom(sb.toString().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getTruck_license_plate()!=null){
                    GateDataBody.setTruckLicensePlate(ByteString.copyFrom(ocrDataGtResponse.getBody().getTruck_license_plate().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getDriver_card_number()!=null){
                    GateDataBody.setDriverCardNumber(ByteString.copyFrom(ocrDataGtResponse.getBody().getDriver_card_number().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getGross_weight()!=0){
                    GateDataBody.setGrossWeight(ByteString.copyFrom(String.valueOf(ocrDataGtResponse.getBody().getGross_weight()).getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getCargo_card_number()!=0){
                    GateDataBody.setCargoCardNumber(ByteString.copyFrom(String.valueOf(ocrDataGtResponse.getBody().getCargo_card_number()).getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getVerified_ocr_data()!=null){
                    if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits()!=null){
                        if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit()!=null){
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getIso_code()!=null){
                                unitOcrData.setIsoCode(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getIso_code().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getPosition()!=null){
                                unitOcrData.setPosition(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getPosition().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDoor_direction()!=null){
                                unitOcrData.setDoorDirection(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDoor_direction().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getClip_on()!=null){
                                unitOcrData.setClipOn(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getClip_on().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getTank_rail()!=null){
                                unitOcrData.setTankRail(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getTank_rail().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getSeal_presence()!=null){
                                unitOcrData.setSealPresence(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getSeal_presence().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getE_seal_state()!=null){
                                unitOcrData.setESealState(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getE_seal_state().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getOverheight()!=null){
                                unitOcrData.setOverheight(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getOverheight().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDg_presence()!=null){
                                unitOcrData.setDgPresence(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDg_presence().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDg_class()!=null){
                                unitOcrData.setDgClass(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDg_class().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getFlatrack_header_down()!=null){
                                unitOcrData.setFlatrackHeaderDown(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getFlatrack_header_down().getBytes("gbk")));
                            }
                            if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber()!=null){

                                int f=ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().size();
                                for(int j=0;j<f;j++){
                                    Gos.UnitOcrData.UnitBundle.Builder unitBundle= Gos.UnitOcrData.UnitBundle.newBuilder();
                                    if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().get(f).getBundle_master()!=null){
                                        unitBundle.setBundleMaster(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().get(f).getBundle_master().getBytes("gbk"))) ;
                                    }
                                    if(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().get(f).getUnit_number()!=null){
                                        unitBundle.setUnitNumber(ByteString.copyFrom(ocrDataGtResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().get(f).getUnit_number().getBytes("gbk"))) ;
                                    }
                                    unitOcrData.addUnitBundleArr(unitBundle);
                                }
                            }
                        }
                    }
                }
                if (ocrDataGtResponse.getBody().getPick_up_position()!=null){
                    GateDataBody.setPickUpPosition(ByteString.copyFrom(ocrDataGtResponse.getBody().getPick_up_position().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getSeal_number()!=null){
                    GateDataBody.setSealNumber(ByteString.copyFrom(ocrDataGtResponse.getBody().getSeal_number().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getNon_containerized_cargo()!=null){
                    GateDataBody.setNonContainerizedCargo(ByteString.copyFrom(ocrDataGtResponse.getBody().getNon_containerized_cargo().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getIndicate_pick_up_position()!=null){
                    GateDataBody.setIndicatePickUpPosition(ByteString.copyFrom(ocrDataGtResponse.getBody().getIndicate_pick_up_position().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getEnter_seal_number()!=null){
                    GateDataBody.setEnterSealNumber(ByteString.copyFrom(ocrDataGtResponse.getBody().getEnter_seal_number().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getScreen_message()!=null){
                    if(ocrDataGtResponse.getBody().getScreen_message().getScreen_message_text()!=null){
                        screenMessage.setScreenMessageText(ByteString.copyFrom(ocrDataGtResponse.getBody().getScreen_message().getScreen_message_text().getBytes("gbk")));
                    }
                    if(ocrDataGtResponse.getBody().getScreen_message().getScreen_message_code()!=0){
                        screenMessage.setScreenMessageCode(ocrDataGtResponse.getBody().getScreen_message().getScreen_message_code());
                    }
                    if(ocrDataGtResponse.getBody().getScreen_message().getConfirmation_button()!=null){
                        screenMessage.setConfirmationButton(ByteString.copyFrom(ocrDataGtResponse.getBody().getScreen_message().getConfirmation_button().getBytes("gbk")));
                    }
                }
                if (ocrDataGtResponse.getBody().getPrint()!=null){
                    GateDataBody.setPrint(ByteString.copyFrom(ocrDataGtResponse.getBody().getPrint().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getPrint_data()!=null){
                    GateDataBody.setPrintData(ByteString.copyFrom(ocrDataGtResponse.getBody().getPrint_data().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getManual_check()!=null){
                    GateDataBody.setManualCheck(ByteString.copyFrom(ocrDataGtResponse.getBody().getManual_check().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getMessage()!=null){
                    GateDataBody.setManualMessage(ByteString.copyFrom(ocrDataGtResponse.getBody().getMessage().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getOpen_barrier()!=null){
                    GateDataBody.setOpenBarrier(ByteString.copyFrom(ocrDataGtResponse.getBody().getOpen_barrier().getBytes("gbk")));
                }
                if (ocrDataGtResponse.getBody().getProcess_end()!=null){
                    GateDataBody.setProcessEnd(ByteString.copyFrom(ocrDataGtResponse.getBody().getProcess_end().getBytes("gbk")));
                }
            }
            GateDataBody.setVerifiedOcrData(0,unitOcrData);
            GateDataBody.setScreenMessage(screenMessage);

            GateData.setBody(GateDataBody);






        }else {
            logger.info("第三方没有返回数据"+ocrDataGtResponse);
        }

        Gos.GOSAny gosAny =Gos.GOSAny.newBuilder().setMsgType(Gos.MSG_TYPE.MSG_OCRDATAGT).setVisitId(gosAny1.getVisitId()).setModuleName(gosAny1.getModuleName())
                .setID(1)
                .setAny(Any.pack(GateData.build()))
                .build();
        logger.info("转换成gooler proto格式"+gosAny);
        return gosAny;
    }

    private GateData toOcrDataGt(Gos.GOSAny gosAny) throws InvalidProtocolBufferException, UnsupportedEncodingException {
        Gos.OcrDataGT OcrDataGtProTo=gosAny.getAny().unpack(Gos.OcrDataGT.class);

        String messageID= new String(OcrDataGtProTo.getHeader().getMessageID().toByteArray(), "gbk");
        String messageType= new String(OcrDataGtProTo.getHeader().getMessageType().toByteArray(), "gbk");
        String timestamp= new String(OcrDataGtProTo.getHeader().getTimestamp().toByteArray(), "gbk");
        String transaction= new String(OcrDataGtProTo.getHeader().getTransaction().toByteArray(), "gbk");
        String visitID= new String(OcrDataGtProTo.getHeader().getVisitID().toByteArray(), "gbk");
        String lane= new String(OcrDataGtProTo.getHeader().getLane().toByteArray(), "gbk");
        Header header=new Header();
        // heard 头
        header.setTransaction(transaction);
        header.setVisitID(visitID);
        header.setMessageType(messageType);
        header.setLane(lane);
        header.setTimestamp(timestamp);
        header.setMessageID(messageID);

        GateData GateData=new GateData();

        GateDataModel GateDataModel =new GateDataModel();


        int r=OcrDataGtProTo.getBody().getRfidLicensePlateTag().size();

        String rfid_license_plate_tag=new String(OcrDataGtProTo.getBody().getRfidLicensePlateTag().toByteArray(),"gbk");
        List<String> stringList= new ArrayList<>();
        String[]  strs=rfid_license_plate_tag.split(",");
        for(int i=0,len=strs.length;i<len;i++){
            stringList.add(strs[i].toString());
        }
        GateDataModel.setRfid_license_plate_tag(stringList);
        GateDataModel.setTruck_license_plate(new String(OcrDataGtProTo.getBody().getTruckLicensePlate().toByteArray(),"gbk"));
        GateDataModel.setDriver_card_number(new String(OcrDataGtProTo.getBody().getDriverCardNumber().toByteArray(),"gbk"));
        GateDataModel.setGross_weight(Integer.parseInt(new String(OcrDataGtProTo.getBody().getGrossWeight().toByteArray(),"gbk")));
        GateDataModel.setCargo_card_number(Integer.parseInt(new String(OcrDataGtProTo.getBody().getCargoCardNumber().toByteArray(),"gbk")));
        GateDataModel.setPick_up_position(new String(OcrDataGtProTo.getBody().getPickUpPosition().toByteArray(),"gbk"));
        GateDataModel.setSeal_number(new String(OcrDataGtProTo.getBody().getSealNumber().toByteArray(),"gbk"));
        GateDataModel.setNon_containerized_cargo(new String(OcrDataGtProTo.getBody().getNonContainerizedCargo().toByteArray(),"gbk"));
        GateDataModel.setIndicate_pick_up_position(new String(OcrDataGtProTo.getBody().getIndicatePickUpPosition().toByteArray(),"gbk"));
        GateDataModel.setEnter_seal_number(new String(OcrDataGtProTo.getBody().getEnterSealNumber().toByteArray(),"gbk"));
        GateDataModel.setPrint(new String(OcrDataGtProTo.getBody().getPrint().toByteArray(),"gbk"));
        GateDataModel.setPrint_data(new String(OcrDataGtProTo.getBody().getPrintData().toByteArray(),"gbk"));
        GateDataModel.setOpen_barrier(new String(OcrDataGtProTo.getBody().getOpenBarrier().toByteArray(),"gbk"));
        GateDataModel.setProcess_end(new String(OcrDataGtProTo.getBody().getProcessEnd().toByteArray(),"gbk"));
        GateDataModel.setMessage(new String(OcrDataGtProTo.getBody().getManualMessage().toByteArray(),"gbk"));
        GateDataModel.setManual_check(new String(OcrDataGtProTo.getBody().getManualCheck().toByteArray(),"gbk"));

        VerifiedOcrData verifiedOcrData =new VerifiedOcrData();


        VerifiedOcrDataUnits verifiedOcrDataUnits =new VerifiedOcrDataUnits();

        VerifiedOcrDataUnitsUnit verifiedOcrDataUnitsUnit =new VerifiedOcrDataUnitsUnit();

        int u=OcrDataGtProTo.getBody().getVerifiedOcrDataCount();

        for(int e=0;e<u;e++){
            int p=OcrDataGtProTo.getBody().getVerifiedOcrData(e).getUnitBundleArrCount();
            List<VerifiedOcrDataUnitsUnitUnitNumber> list =new ArrayList<>();
            for(int i=0;i<p;i++){
                VerifiedOcrDataUnitsUnitUnitNumber verifiedOcrDataUnitsUnitUnitNumber=new VerifiedOcrDataUnitsUnitUnitNumber();
                verifiedOcrDataUnitsUnitUnitNumber.setBundle_master(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getUnitBundleArr(i).getBundleMaster().toByteArray(),"gbk"));
                verifiedOcrDataUnitsUnitUnitNumber.setUnit_number(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getUnitBundleArr(i).getUnitNumber().toByteArray(),"gbk"));
                list.add(verifiedOcrDataUnitsUnitUnitNumber);
            }
            verifiedOcrDataUnitsUnit.setIso_code(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getIsoCode().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setPosition(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getPosition().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setDoor_direction(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getDoorDirection().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setClip_on(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getClipOn().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setTank_rail(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getTankRail().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setSeal_presence(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getSealPresence().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setE_seal_state(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getESealState().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setOverheight(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getOverheight().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setDg_presence(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getDgPresence().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setDg_class(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getDgClass().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setFlatrack_header_down(new String(OcrDataGtProTo.getBody().getVerifiedOcrData(e).getFlatrackHeaderDown().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setVerifiedOcrDataUnitsUnitUnitNumber(list);

        }


        verifiedOcrDataUnits.setUnit(verifiedOcrDataUnitsUnit);




        GateDataScreenMessage gateDataScreenMessage=new GateDataScreenMessage();
        gateDataScreenMessage.setScreen_message_text(new String(OcrDataGtProTo.getBody().getScreenMessage().getScreenMessageText().toByteArray(),"gbk"));
        gateDataScreenMessage.setScreen_message_code(OcrDataGtProTo.getBody().getScreenMessage().getScreenMessageCode());
        gateDataScreenMessage.setConfirmation_button(new String(OcrDataGtProTo.getBody().getScreenMessage().getConfirmationButton().toByteArray(),"gbk"));



        GateDataModel.setScreen_message(gateDataScreenMessage);
        GateDataModel.setVerified_ocr_data(verifiedOcrData);
        GateData.setBody(GateDataModel);
        GateData.setHeader(header);
        return  GateData;

    }


    private Gos.GOSAny toGateDataResponse(GateData gateData, Gos.GOSAny gosAny1) throws UnsupportedEncodingException {
        GateDataResponse gateDataResponse= null;

        logger.info("调用DamageInspection第三方接口");
        UniResult u= gateDataService.toWebService(gateData);

        if (Status.OK.equals(u.getCode()) ){

            gateDataResponse= (GateDataResponse) u.getDataObject();

        }else {
            gateDataResponse= (GateDataResponse) u.getDataObject();

        }
        Gos.GateData.Builder GateData= Gos.GateData.newBuilder();

        Gos.Header.Builder header =Gos.Header.newBuilder();

        Gos.Error.Builder errorder= Gos.Error.newBuilder();

        Gos.GateDataBody.Builder GateDataBody= Gos.GateDataBody.newBuilder();

        Gos.UnitOcrData.Builder unitOcrData= Gos.UnitOcrData.newBuilder();


        Gos.ScreenMessage.Builder screenMessage= Gos.ScreenMessage.newBuilder();




        if(gateDataResponse!=null){
            if(gateDataResponse.getHeader()!=null){
                if(gateDataResponse.getHeader().getMessageType()!=null){header.setMessageType(ByteString.copyFrom(gateDataResponse.getHeader().getMessageType().getBytes("gbk")));}
                if(gateDataResponse.getHeader().getMessageID()!=null){header.setMessageID(ByteString.copyFrom(gateDataResponse.getHeader().getMessageID().getBytes("gbk")));}
                if(gateDataResponse.getHeader().getTimestamp()!=null){header.setTimestamp(ByteString.copyFrom(gateDataResponse.getHeader().getTimestamp().getBytes("gbk")));}
                if(gateDataResponse.getHeader().getTransaction()!=null){header.setTransaction(ByteString.copyFrom(gateDataResponse.getHeader().getTransaction().getBytes("gbk")));}
                if(gateDataResponse.getHeader().getVisitID()!=null){header.setVisitID(ByteString.copyFrom(gateDataResponse.getHeader().getVisitID().getBytes("gbk")));}
                if(gateDataResponse.getHeader().getLane()!=null){header.setLane(ByteString.copyFrom(gateDataResponse.getHeader().getLane().getBytes("gbk")));}
                if(gateDataResponse.getHeader().getError()!=null){
                    if(gateDataResponse.getHeader().getError().getError_code()!=0){errorder.setErrorCode(gateDataResponse.getHeader().getError().getError_code());}
                    if(gateDataResponse.getHeader().getError().getError_message()!=null){errorder.setErrorMessage(ByteString.copyFrom(gateDataResponse.getHeader().getError().getError_message().getBytes("gbk")));}

                }
            }


            header.addErrArr(errorder);
            GateData.setHeader(header);
            if (gateDataResponse.getBody()!=null){



                if (gateDataResponse.getBody().getRfid_license_plate_tag()!=null){

                    int y=gateDataResponse.getBody().getRfid_license_plate_tag().size();
                    StringBuilder sb = new StringBuilder();
                    for(int r=0;r<y;r++){
                        String e= gateDataResponse.getBody().getRfid_license_plate_tag().get(r);
                        if(e.length()>0){
                            sb.append(",");
                        }
                        sb.append(e);
                    }

                    GateDataBody.setRfidLicensePlateTag(ByteString.copyFrom(sb.toString().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getTruck_license_plate()!=null){
                    GateDataBody.setTruckLicensePlate(ByteString.copyFrom(gateDataResponse.getBody().getTruck_license_plate().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getDriver_card_number()!=null){
                    GateDataBody.setDriverCardNumber(ByteString.copyFrom(gateDataResponse.getBody().getDriver_card_number().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getGross_weight()!=0){
                    GateDataBody.setGrossWeight(ByteString.copyFrom(String.valueOf(gateDataResponse.getBody().getGross_weight()).getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getCargo_card_number()!=0){
                    GateDataBody.setCargoCardNumber(ByteString.copyFrom(String.valueOf(gateDataResponse.getBody().getCargo_card_number()).getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getVerified_ocr_data()!=null){
                    if(gateDataResponse.getBody().getVerified_ocr_data().getUnits()!=null){
                        if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit()!=null){
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getIso_code()!=null){
                                unitOcrData.setIsoCode(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getIso_code().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getPosition()!=null){
                                unitOcrData.setPosition(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getPosition().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDoor_direction()!=null){
                                unitOcrData.setDoorDirection(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDoor_direction().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getClip_on()!=null){
                                unitOcrData.setClipOn(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getClip_on().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getTank_rail()!=null){
                                unitOcrData.setTankRail(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getTank_rail().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getSeal_presence()!=null){
                                unitOcrData.setSealPresence(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getSeal_presence().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getE_seal_state()!=null){
                                unitOcrData.setESealState(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getE_seal_state().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getOverheight()!=null){
                                unitOcrData.setOverheight(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getOverheight().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDg_presence()!=null){
                                unitOcrData.setDgPresence(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDg_presence().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDg_class()!=null){
                                unitOcrData.setDgClass(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getDg_class().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getFlatrack_header_down()!=null){
                                unitOcrData.setFlatrackHeaderDown(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getFlatrack_header_down().getBytes("gbk")));
                            }
                            if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber()!=null){

                                    int f=gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().size();
                                    for(int j=0;j<f;j++){
                                        Gos.UnitOcrData.UnitBundle.Builder unitBundle= Gos.UnitOcrData.UnitBundle.newBuilder();
                                        if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().get(f).getBundle_master()!=null){
                                            unitBundle.setBundleMaster(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().get(f).getBundle_master().getBytes("gbk"))) ;
                                        }
                                        if(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().get(f).getUnit_number()!=null){
                                            unitBundle.setUnitNumber(ByteString.copyFrom(gateDataResponse.getBody().getVerified_ocr_data().getUnits().getUnit().getVerifiedOcrDataUnitsUnitUnitNumber().get(f).getUnit_number().getBytes("gbk"))) ;
                                        }
                                        unitOcrData.addUnitBundleArr(unitBundle);
                                    }
                            }
                        }
                    }
                }
                if (gateDataResponse.getBody().getPick_up_position()!=null){
                    GateDataBody.setPickUpPosition(ByteString.copyFrom(gateDataResponse.getBody().getPick_up_position().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getSeal_number()!=null){
                    GateDataBody.setSealNumber(ByteString.copyFrom(gateDataResponse.getBody().getSeal_number().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getNon_containerized_cargo()!=null){
                    GateDataBody.setNonContainerizedCargo(ByteString.copyFrom(gateDataResponse.getBody().getNon_containerized_cargo().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getIndicate_pick_up_position()!=null){
                    GateDataBody.setIndicatePickUpPosition(ByteString.copyFrom(gateDataResponse.getBody().getIndicate_pick_up_position().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getEnter_seal_number()!=null){
                    GateDataBody.setEnterSealNumber(ByteString.copyFrom(gateDataResponse.getBody().getEnter_seal_number().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getScreen_message()!=null){
                   if(gateDataResponse.getBody().getScreen_message().getScreen_message_text()!=null){
                       screenMessage.setScreenMessageText(ByteString.copyFrom(gateDataResponse.getBody().getScreen_message().getScreen_message_text().getBytes("gbk")));
                   }
                    if(gateDataResponse.getBody().getScreen_message().getScreen_message_code()!=0){
                        screenMessage.setScreenMessageCode(gateDataResponse.getBody().getScreen_message().getScreen_message_code());
                    }
                    if(gateDataResponse.getBody().getScreen_message().getConfirmation_button()!=null){
                        screenMessage.setConfirmationButton(ByteString.copyFrom(gateDataResponse.getBody().getScreen_message().getConfirmation_button().getBytes("gbk")));
                    }
                }
                if (gateDataResponse.getBody().getPrint()!=null){
                    GateDataBody.setPrint(ByteString.copyFrom(gateDataResponse.getBody().getPrint().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getPrint_data()!=null){
                    GateDataBody.setPrintData(ByteString.copyFrom(gateDataResponse.getBody().getPrint_data().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getManual_check()!=null){
                    GateDataBody.setManualCheck(ByteString.copyFrom(gateDataResponse.getBody().getManual_check().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getMessage()!=null){
                    GateDataBody.setManualMessage(ByteString.copyFrom(gateDataResponse.getBody().getMessage().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getOpen_barrier()!=null){
                    GateDataBody.setOpenBarrier(ByteString.copyFrom(gateDataResponse.getBody().getOpen_barrier().getBytes("gbk")));
                }
                if (gateDataResponse.getBody().getProcess_end()!=null){
                    GateDataBody.setProcessEnd(ByteString.copyFrom(gateDataResponse.getBody().getProcess_end().getBytes("gbk")));
                }
            }
            GateDataBody.setVerifiedOcrData(0,unitOcrData);
            GateDataBody.setScreenMessage(screenMessage);

            GateData.setBody(GateDataBody);



        }else {
            logger.info("第三方没有返回数据"+gateDataResponse);
        }

        Gos.GOSAny gosAny =Gos.GOSAny.newBuilder().setMsgType(Gos.MSG_TYPE.MSG_GATEDATA).setVisitId(gosAny1.getVisitId()).setModuleName(gosAny1.getModuleName())
                .setID(1)
                .setAny(Any.pack(GateData.build()))
                .build();
        logger.info("转换成gooler proto格式"+gosAny);
        return gosAny;


    }

    /**
     * to Java Bean
     * @param gosAny
     * @return
     */
    private GateData toGateData(Gos.GOSAny gosAny) throws InvalidProtocolBufferException, UnsupportedEncodingException {
        Gos.GateData GateDataProTo=gosAny.getAny().unpack(Gos.GateData.class);

        String messageID= new String(GateDataProTo.getHeader().getMessageID().toByteArray(), "gbk");
        String messageType= new String(GateDataProTo.getHeader().getMessageType().toByteArray(), "gbk");
        String timestamp= new String(GateDataProTo.getHeader().getTimestamp().toByteArray(), "gbk");
        String transaction= new String(GateDataProTo.getHeader().getTransaction().toByteArray(), "gbk");
        String visitID= new String(GateDataProTo.getHeader().getVisitID().toByteArray(), "gbk");
        String lane= new String(GateDataProTo.getHeader().getLane().toByteArray(), "gbk");
        Header header=new Header();
        // heard 头
        header.setTransaction(transaction);
        header.setVisitID(visitID);
        header.setMessageType(messageType);
        header.setLane(lane);
        header.setTimestamp(timestamp);
        header.setMessageID(messageID);

        GateData GateData=new GateData();

        GateDataModel GateDataModel =new GateDataModel();


        int r=GateDataProTo.getBody().getRfidLicensePlateTag().size();

        String rfid_license_plate_tag=new String(GateDataProTo.getBody().getRfidLicensePlateTag().toByteArray(),"gbk");
        List<String> stringList= new ArrayList<>();
        String[]  strs=rfid_license_plate_tag.split(",");
        for(int i=0,len=strs.length;i<len;i++){
            stringList.add(strs[i].toString());
        }
        GateDataModel.setRfid_license_plate_tag(stringList);
        GateDataModel.setTruck_license_plate(new String(GateDataProTo.getBody().getTruckLicensePlate().toByteArray(),"gbk"));
        GateDataModel.setDriver_card_number(new String(GateDataProTo.getBody().getDriverCardNumber().toByteArray(),"gbk"));
        GateDataModel.setGross_weight(Integer.parseInt(new String(GateDataProTo.getBody().getGrossWeight().toByteArray(),"gbk")));
        GateDataModel.setCargo_card_number(Integer.parseInt(new String(GateDataProTo.getBody().getCargoCardNumber().toByteArray(),"gbk")));
        GateDataModel.setPick_up_position(new String(GateDataProTo.getBody().getPickUpPosition().toByteArray(),"gbk"));
        GateDataModel.setSeal_number(new String(GateDataProTo.getBody().getSealNumber().toByteArray(),"gbk"));
        GateDataModel.setNon_containerized_cargo(new String(GateDataProTo.getBody().getNonContainerizedCargo().toByteArray(),"gbk"));
        GateDataModel.setIndicate_pick_up_position(new String(GateDataProTo.getBody().getIndicatePickUpPosition().toByteArray(),"gbk"));
        GateDataModel.setEnter_seal_number(new String(GateDataProTo.getBody().getEnterSealNumber().toByteArray(),"gbk"));
        GateDataModel.setPrint(new String(GateDataProTo.getBody().getPrint().toByteArray(),"gbk"));
        GateDataModel.setPrint_data(new String(GateDataProTo.getBody().getPrintData().toByteArray(),"gbk"));
        GateDataModel.setOpen_barrier(new String(GateDataProTo.getBody().getOpenBarrier().toByteArray(),"gbk"));
        GateDataModel.setProcess_end(new String(GateDataProTo.getBody().getProcessEnd().toByteArray(),"gbk"));
        GateDataModel.setMessage(new String(GateDataProTo.getBody().getManualMessage().toByteArray(),"gbk"));
        GateDataModel.setManual_check(new String(GateDataProTo.getBody().getManualCheck().toByteArray(),"gbk"));

        VerifiedOcrData verifiedOcrData =new VerifiedOcrData();


        VerifiedOcrDataUnits verifiedOcrDataUnits =new VerifiedOcrDataUnits();

        VerifiedOcrDataUnitsUnit verifiedOcrDataUnitsUnit =new VerifiedOcrDataUnitsUnit();

        int u=GateDataProTo.getBody().getVerifiedOcrDataCount();

        for(int e=0;e<u;e++){
            int p=GateDataProTo.getBody().getVerifiedOcrData(e).getUnitBundleArrCount();
            List<VerifiedOcrDataUnitsUnitUnitNumber> list =new ArrayList<>();
            for(int i=0;i<p;i++){
                VerifiedOcrDataUnitsUnitUnitNumber verifiedOcrDataUnitsUnitUnitNumber=new VerifiedOcrDataUnitsUnitUnitNumber();
                verifiedOcrDataUnitsUnitUnitNumber.setBundle_master(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getUnitBundleArr(i).getBundleMaster().toByteArray(),"gbk"));
                verifiedOcrDataUnitsUnitUnitNumber.setUnit_number(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getUnitBundleArr(i).getUnitNumber().toByteArray(),"gbk"));
                list.add(verifiedOcrDataUnitsUnitUnitNumber);
            }
            verifiedOcrDataUnitsUnit.setIso_code(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getIsoCode().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setPosition(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getPosition().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setDoor_direction(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getDoorDirection().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setClip_on(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getClipOn().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setTank_rail(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getTankRail().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setSeal_presence(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getSealPresence().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setE_seal_state(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getESealState().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setOverheight(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getOverheight().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setDg_presence(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getDgPresence().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setDg_class(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getDgClass().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setFlatrack_header_down(new String(GateDataProTo.getBody().getVerifiedOcrData(e).getFlatrackHeaderDown().toByteArray(),"gbk"));
            verifiedOcrDataUnitsUnit.setVerifiedOcrDataUnitsUnitUnitNumber(list);

        }


        verifiedOcrDataUnits.setUnit(verifiedOcrDataUnitsUnit);




        GateDataScreenMessage gateDataScreenMessage=new GateDataScreenMessage();
        gateDataScreenMessage.setScreen_message_text(new String(GateDataProTo.getBody().getScreenMessage().getScreenMessageText().toByteArray(),"gbk"));
        gateDataScreenMessage.setScreen_message_code(GateDataProTo.getBody().getScreenMessage().getScreenMessageCode());
        gateDataScreenMessage.setConfirmation_button(new String(GateDataProTo.getBody().getScreenMessage().getConfirmationButton().toByteArray(),"gbk"));



        GateDataModel.setScreen_message(gateDataScreenMessage);
        GateDataModel.setVerified_ocr_data(verifiedOcrData);
        GateData.setBody(GateDataModel);
        GateData.setHeader(header);


        return GateData;
    }

    /**
     * toLaneEntryResponse
     * @param laneEntry
     * @param gosAny1
     * @return
     * @throws UnsupportedEncodingException
     */
    private Gos.GOSAny toLaneEntryResponse(LaneEntry laneEntry, Gos.GOSAny gosAny1) throws UnsupportedEncodingException {

        LaneEntryResponse laneEntryResponse= null;

        logger.info("调用DamageInspection第三方接口");
        UniResult u= laneEntryService.toWebService(laneEntry);

        if (Status.OK.equals(u.getCode()) ){

            laneEntryResponse= (LaneEntryResponse) u.getDataObject();

        }else {
            laneEntryResponse= (LaneEntryResponse) u.getDataObject();

        }
        Gos.LaneEntry.Builder LaneEntryBuilder= Gos.LaneEntry.newBuilder();

        Gos.Header.Builder header =Gos.Header.newBuilder();

        Gos.Error.Builder errorder= Gos.Error.newBuilder();

        Gos.LaneEntryBody.Builder LaneEntryBody= Gos.LaneEntryBody.newBuilder();
        Gos.ScreenMessage.Builder ScreenMessage= Gos.ScreenMessage.newBuilder();


        if(laneEntryResponse!=null){
            if(laneEntryResponse.getHeader()!=null){
                if(laneEntryResponse.getHeader().getMessageType()!=null){header.setMessageType(ByteString.copyFrom(laneEntryResponse.getHeader().getMessageType().getBytes("gbk")));}
                if(laneEntryResponse.getHeader().getMessageID()!=null){header.setMessageID(ByteString.copyFrom(laneEntryResponse.getHeader().getMessageID().getBytes("gbk")));}
                if(laneEntryResponse.getHeader().getTimestamp()!=null){header.setTimestamp(ByteString.copyFrom(laneEntryResponse.getHeader().getTimestamp().getBytes("gbk")));}
                if(laneEntryResponse.getHeader().getTransaction()!=null){header.setTransaction(ByteString.copyFrom(laneEntryResponse.getHeader().getTransaction().getBytes("gbk")));}
                if(laneEntryResponse.getHeader().getVisitID()!=null){header.setVisitID(ByteString.copyFrom(laneEntryResponse.getHeader().getVisitID().getBytes("gbk")));}
                if(laneEntryResponse.getHeader().getLane()!=null){header.setLane(ByteString.copyFrom(laneEntryResponse.getHeader().getLane().getBytes("gbk")));}
                if(laneEntryResponse.getHeader().getError()!=null){
                    if(laneEntryResponse.getHeader().getError().getError_code()!=0){errorder.setErrorCode(laneEntryResponse.getHeader().getError().getError_code());}
                    if(laneEntryResponse.getHeader().getError().getError_message()!=null){errorder.setErrorMessage(ByteString.copyFrom(laneEntryResponse.getHeader().getError().getError_message().getBytes("gbk")));}

                }
            }
            if(laneEntryResponse.getLaneEntryModel()!=null){
                if(laneEntryResponse.getLaneEntryModel().getProcess_start_timestamp()!=null){
                    LaneEntryBody.setProcessStartTimestamp(ByteString.copyFrom(laneEntryResponse.getLaneEntryModel().getProcess_start_timestamp().getBytes("gbk")));
                }
                if(laneEntryResponse.getLaneEntryModel().getRfid_license_plate_tag()!=null){
                    int y=laneEntryResponse.getLaneEntryModel().getRfid_license_plate_tag().size();
                    StringBuilder sb = new StringBuilder();
                    for(int r=0;r<y;r++){
                      String e=  laneEntryResponse.getLaneEntryModel().getRfid_license_plate_tag().get(r);
                       if(e.length()>0){
                           sb.append(",");
                       }
                        sb.append(e);
                    }
                    LaneEntryBody.setRfidLicensePlateTag(ByteString.copyFrom(sb.toString().getBytes("gbk")));
                }
                if(laneEntryResponse.getLaneEntryModel().getTruck_license_plate()!=null){LaneEntryBody.setTruckLicensePlate(ByteString.copyFrom(laneEntryResponse.getLaneEntryModel().getTruck_license_plate().getBytes("gbk")));}
                if(laneEntryResponse.getLaneEntryModel().getHas_driver_card()!=null){LaneEntryBody.setHasDriverCard(ByteString.copyFrom(laneEntryResponse.getLaneEntryModel().getHas_driver_card().getBytes("gbk")));}
                if(laneEntryResponse.getLaneEntryModel().getDriver_card_number()!=0){LaneEntryBody.setDriverCardNumber(ByteString.copyFrom(String.valueOf(laneEntryResponse.getLaneEntryModel().getDriver_card_number()).getBytes("gbk")));}
                if(laneEntryResponse.getLaneEntryModel().getScreen_message()!=null){
                    if(laneEntryResponse.getLaneEntryModel().getScreen_message().getConfirmation_button()!=null){
                        ScreenMessage.setConfirmationButton(ByteString.copyFrom(laneEntryResponse.getLaneEntryModel().getScreen_message().getConfirmation_button().getBytes("gbk")));
                    }
                    if(laneEntryResponse.getLaneEntryModel().getScreen_message().getScreen_message_code()!=0){
                        ScreenMessage.setScreenMessageCode(laneEntryResponse.getLaneEntryModel().getScreen_message().getScreen_message_code());
                    }
                    if(laneEntryResponse.getLaneEntryModel().getScreen_message().getScreen_message_text()!=null){
                        ScreenMessage.setScreenMessageText(ByteString.copyFrom(laneEntryResponse.getLaneEntryModel().getScreen_message().getScreen_message_text().getBytes("gbk")));
                    }

                }
                if(laneEntryResponse.getLaneEntryModel().getPrint()!=null){
                    LaneEntryBody.setPrint(ByteString.copyFrom(String.valueOf(laneEntryResponse.getLaneEntryModel().getPrint()).getBytes("gbk")));
                }
                if(laneEntryResponse.getLaneEntryModel().getPrint_data()!=null){
                    LaneEntryBody.setPrintData(ByteString.copyFrom(String.valueOf(laneEntryResponse.getLaneEntryModel().getPrint_data()).getBytes("gbk")));
                }
                if(laneEntryResponse.getLaneEntryModel().getManual_check()!=null){
                    LaneEntryBody.setManualCheck(ByteString.copyFrom(String.valueOf(laneEntryResponse.getLaneEntryModel().getManual_check()).getBytes("gbk")));
                }
                if(laneEntryResponse.getLaneEntryModel().getOpen_barrier()!=null){
                    LaneEntryBody.setOpenBarrier(ByteString.copyFrom(String.valueOf(laneEntryResponse.getLaneEntryModel().getOpen_barrier()).getBytes("gbk")));
                }
                if(laneEntryResponse.getLaneEntryModel().getProcess_end()!=null){
                    LaneEntryBody.setProcessEnd(ByteString.copyFrom(String.valueOf(laneEntryResponse.getLaneEntryModel().getProcess_end()).getBytes("gbk")));
                }
            }

            header.addErrArr(errorder);
            LaneEntryBuilder.setHeader(header);
            LaneEntryBody.setScreenMessage(ScreenMessage);
            LaneEntryBuilder.setBody(LaneEntryBody);


        }else {
            logger.info("第三方没有返回数据"+laneEntryResponse);
        }

        Gos.GOSAny gosAny =Gos.GOSAny.newBuilder().setMsgType(Gos.MSG_TYPE.MSG_LANEENTRY).setVisitId(gosAny1.getVisitId()).setModuleName(gosAny1.getModuleName())
                .setID(1)
                .setAny(Any.pack(LaneEntryBuilder.build()))
                .build();
        logger.info("转换成gooler proto格式"+gosAny);
        return gosAny;


    }

    private LaneEntry tolaneEntry(Gos.GOSAny gosAny) throws InvalidProtocolBufferException, UnsupportedEncodingException {
        Gos.LaneEntry LaneEntryProTo=gosAny.getAny().unpack(Gos.LaneEntry.class);

        String messageID= new String(LaneEntryProTo.getHeader().getMessageID().toByteArray(), "gbk");
        String messageType= new String(LaneEntryProTo.getHeader().getMessageType().toByteArray(), "gbk");
        String timestamp= new String(LaneEntryProTo.getHeader().getTimestamp().toByteArray(), "gbk");
        String transaction= new String(LaneEntryProTo.getHeader().getTransaction().toByteArray(), "gbk");
        String visitID= new String(LaneEntryProTo.getHeader().getVisitID().toByteArray(), "gbk");
        String lane= new String(LaneEntryProTo.getHeader().getLane().toByteArray(), "gbk");
        Header header=new Header();
        // heard 头
        header.setTransaction(transaction);
        header.setVisitID(visitID);
        header.setMessageType(messageType);
        header.setLane(lane);
        header.setTimestamp(timestamp);
        header.setMessageID(messageID);

        LaneEntry LaneEntry=new LaneEntry();
        LaneEntryModel LaneEntryModel=new LaneEntryModel();
        LaneEntryScreenMessage LaneEntryScreenMessage=new LaneEntryScreenMessage();



        String confirmation_button=new String(LaneEntryProTo.getBody().getScreenMessage().getConfirmationButton().toByteArray(),"gbk");
        int screen_message_code=LaneEntryProTo.getBody().getScreenMessage().getScreenMessageCode();
        String screen_message_text=new String(LaneEntryProTo.getBody().getScreenMessage().getScreenMessageText().toByteArray(),"gbk");


        LaneEntryScreenMessage.setConfirmation_button(confirmation_button);
        LaneEntryScreenMessage.setScreen_message_code(screen_message_code);
        LaneEntryScreenMessage.setScreen_message_text(screen_message_text);



        String truck_license_plate=new String(LaneEntryProTo.getBody().getTruckLicensePlate().toByteArray(),"gbk");
        String process_start_timestamp=new String(LaneEntryProTo.getBody().getProcessStartTimestamp().toByteArray(),"gbk");
        String has_driver_card=new String(LaneEntryProTo.getBody().getHasDriverCard().toByteArray(),"gbk");
        String driver_card_number=new String(LaneEntryProTo.getBody().getDriverCardNumber().toByteArray(),"gbk");
        String print=new String(LaneEntryProTo.getBody().getPrint().toByteArray(),"gbk");
        String print_data=new String(LaneEntryProTo.getBody().getPrintData().toByteArray(),"gbk");
        String open_barrier=new String(LaneEntryProTo.getBody().getOpenBarrier().toByteArray(),"gbk");
        String process_end=new String(LaneEntryProTo.getBody().getProcessEnd().toByteArray(),"gbk");

        LaneEntryModel.setTruck_license_plate(truck_license_plate);
        LaneEntryModel.setProcess_start_timestamp(process_start_timestamp);
        LaneEntryModel.setHas_driver_card(has_driver_card);
        LaneEntryModel.setDriver_card_number(Integer.parseInt(driver_card_number));
        LaneEntryModel.setPrint(print);
        LaneEntryModel.setPrint_data(print_data);
        LaneEntryModel.setOpen_barrier(open_barrier);
        LaneEntryModel.setProcess_end(process_end);
        LaneEntryModel.setScreen_message(LaneEntryScreenMessage);


        LaneEntry.setHeader(header);
        LaneEntry.setLaneEntryModel(LaneEntryModel);

        return  LaneEntry;
    }


    /**
     *  OcrData 请求第三方 然后发队列
     * @param ocrData
     * @param gosAny1
     * @return
     */
    private Gos.GOSAny toOcrDataResponse(OcrData ocrData, Gos.GOSAny gosAny1) throws UnsupportedEncodingException {

        OcrDataResponse ocrDataResponse= null;

        logger.info("调用DamageInspection第三方接口");
        UniResult u= ocrDataProtoService.toWebService(ocrData);

        if (Status.OK.equals(u.getCode()) ){

            ocrDataResponse= (OcrDataResponse) u.getDataObject();

        }else {
            ocrDataResponse= (OcrDataResponse) u.getDataObject();

        }
        Gos.OcrData.Builder OcrDataBuilder= Gos.OcrData.newBuilder();

        Gos.Header.Builder header =Gos.Header.newBuilder();

        Gos.Error.Builder errorder= Gos.Error.newBuilder();

        if(ocrDataResponse!=null){
            if(ocrDataResponse.getHeader()!=null){
                if(ocrDataResponse.getHeader().getMessageType()!=null){header.setMessageType(ByteString.copyFrom(ocrDataResponse.getHeader().getMessageType().getBytes("gbk")));}
                if(ocrDataResponse.getHeader().getMessageID()!=null){header.setMessageID(ByteString.copyFrom(ocrDataResponse.getHeader().getMessageID().getBytes("gbk")));}
                if(ocrDataResponse.getHeader().getTimestamp()!=null){header.setTimestamp(ByteString.copyFrom(ocrDataResponse.getHeader().getTimestamp().getBytes("gbk")));}
                if(ocrDataResponse.getHeader().getTransaction()!=null){header.setTransaction(ByteString.copyFrom(ocrDataResponse.getHeader().getTransaction().getBytes("gbk")));}
                if(ocrDataResponse.getHeader().getVisitID()!=null){header.setVisitID(ByteString.copyFrom(ocrDataResponse.getHeader().getVisitID().getBytes("gbk")));}
                if(ocrDataResponse.getHeader().getLane()!=null){header.setLane(ByteString.copyFrom(ocrDataResponse.getHeader().getLane().getBytes("gbk")));}
                if(ocrDataResponse.getHeader().getError()!=null){
                    if(ocrDataResponse.getHeader().getError().getError_code()!=0){errorder.setErrorCode(ocrDataResponse.getHeader().getError().getError_code());}
                    if(ocrDataResponse.getHeader().getError().getError_message()!=null){errorder.setErrorMessage(ByteString.copyFrom(ocrDataResponse.getHeader().getError().getError_message().getBytes("gbk")));}

                }
            }
            header.addErrArr(errorder);
            OcrDataBuilder.setHeader(header);

        }else {
            logger.info("第三方没有返回数据"+ocrDataResponse);
        }

        Gos.GOSAny gosAny =Gos.GOSAny.newBuilder().setMsgType(Gos.MSG_TYPE.MSG_OCRDATA).setVisitId(gosAny1.getVisitId()).setModuleName(gosAny1.getModuleName())
                .setID(1)
                .setAny(Any.pack(OcrDataBuilder.build()))
                .build();
        logger.info("转换成gooler proto格式"+gosAny);
        return gosAny;
    }

    /**
     * OcrData接口转JavaBean格式
     * @return
     */
    private OcrData toOcrData(Gos.GOSAny gosAny) throws InvalidProtocolBufferException, UnsupportedEncodingException {


        //数据结构图


             //  Header
        // OcrData
            //  OcrDataModel
                    // OriginalOcrData  list<OrginalOcUnits>
                              //OrginalOcUnits
                                    //OrginalOcrUnit
                    // UpdatedOcrData
                             //UpdatedOcrDataUnits  list<UpdatedOcrDataUnit>
                                    // UpdatedOcrDataUnit list<UnitNumber>
                                             // UnitNumber




        // 调用第三方model
        OcrData ocrData=new OcrData();
        // 调用第三方model 的Header属性对象
        Header header=new Header();
        // 调用第三方model 的OcrDataModel属性对象
        OcrDataModel ocrDataModel=new OcrDataModel();
        // 调用OcrDataModel 的OriginalOcrData属性对象
        OriginalOcrData originalOcrData=new OriginalOcrData();
        // 调用OcrDataModel 的OriginalOcrData属性对象
        UpdatedOcrData updatedOcrData=new UpdatedOcrData();
        // 调用UpdatedOcrData 的OriginalOcrData属性对象
        UpdatedOcrDataUnits UpdatedOcrDataUnits=new UpdatedOcrDataUnits();



        // zmq 发过来的消息
        Gos.OcrData ocrDataProTo=gosAny.getAny().unpack(Gos.OcrData.class);
        // 去除对应参数
        String messageID= new String(ocrDataProTo.getHeader().getMessageID().toByteArray(), "gbk");
        String messageType= new String(ocrDataProTo.getHeader().getMessageType().toByteArray(), "gbk");
        String timestamp= new String(ocrDataProTo.getHeader().getTimestamp().toByteArray(), "gbk");
        String transaction= new String(ocrDataProTo.getHeader().getTransaction().toByteArray(), "gbk");
        String visitID= new String(ocrDataProTo.getHeader().getVisitID().toByteArray(), "gbk");
        String lane= new String(ocrDataProTo.getHeader().getLane().toByteArray(), "gbk");

        // heard 头
        header.setTransaction(transaction);
        header.setVisitID(visitID);
        header.setMessageType(messageType);
        header.setLane(lane);
        header.setTimestamp(timestamp);
        header.setMessageID(messageID);

        String passage_timestamp= new String(ocrDataProTo.getBody().getPassageTimestamp().toByteArray(),"gbk");
        String rfid_license_plate_tag=new String(ocrDataProTo.getBody().getRfidLicensePlateTag().toByteArray(),"gbk");
        String operator_id=new String(ocrDataProTo.getBody().getOperatorId().toByteArray(),"gbk");
        String snapshot_location=new String(ocrDataProTo.getBody().getSnapshotLocation().toByteArray(),"gbk");





        List<String> stringList= new ArrayList<>();
        String[]  strs=rfid_license_plate_tag.split(",");
        for(int i=0,len=strs.length;i<len;i++){
            stringList.add(strs[i].toString());
        }
        ocrDataModel.setRfid_license_plate_tag(stringList);
        ocrDataModel.setPassage_timestamp(passage_timestamp);
        ocrDataModel.setOperator_id(operator_id);
        ocrDataModel.setSnapshot_location(snapshot_location);

        String truck_license_plate =new String(ocrDataProTo.getBody().getOriginalOcrData().getTruckLicensePlate().toByteArray(),"gbk");



        originalOcrData.setTruck_license_plate(truck_license_plate);

        List<OrginalOcUnits> orginalOcUnitsList=new ArrayList<>();
        int unitsLength=ocrDataProTo.getBody().getOriginalOcrData().getUnitArrCount();
        for(int y=0;y<unitsLength;y++){
            OrginalOcUnits orginalOcUnits=new OrginalOcUnits();

            OrginalOcrUnit orginalOcrUnit=new OrginalOcrUnit();
            String unit_number=new String(ocrDataProTo.getBody().getOriginalOcrData().getUnitArr(y).getUnitNumbers().toByteArray(),"gbk");

            String iso_code =new String(ocrDataProTo.getBody().getOriginalOcrData().getUnitArr(y).getIsoCode().toByteArray(),"gbk");
            String position =new String(ocrDataProTo.getBody().getOriginalOcrData().getUnitArr(y).getPosition().toByteArray(),"gbk");
            String door_direction=new String(ocrDataProTo.getBody().getOriginalOcrData().getUnitArr(y).getDoorDirection().toByteArray(),"gbk");
            String clip_on=new String(ocrDataProTo.getBody().getOriginalOcrData().getUnitArr(y).getDoorDirection().toByteArray(),"gbk" );
            String tank_rail =new String(ocrDataProTo.getBody().getOriginalOcrData().getUnitArr(y).getTankRail().toByteArray(),"gbk");
            String seal_presence=new String(ocrDataProTo.getBody().getOriginalOcrData().getUnitArr(y).getSealPresence().toByteArray(),"gbk");

            orginalOcrUnit.setTank_rail(tank_rail);
            orginalOcrUnit.setSeal_presence(seal_presence);
            orginalOcrUnit.setIso_code(iso_code);
            orginalOcrUnit.setPosition(position);
            orginalOcrUnit.setDoor_direction(door_direction);
            orginalOcrUnit.setClip_on(clip_on);
            List<String> stringList1= new ArrayList<>();
            String[]  strs1=unit_number.split(",");
            for(int i=0,len=strs.length;i<len;i++){
                stringList.add(strs[i].toString());
            }
            orginalOcrUnit.setUnit_number(stringList1);
            orginalOcUnits.setUnit(orginalOcrUnit);
            orginalOcUnitsList.add(orginalOcUnits);
        }

        originalOcrData.setUnits(orginalOcUnitsList);




        String updated_ocr_data_truck_license_plate=new String(ocrDataProTo.getBody().getUpdatedOcrData().getTruckLicensePlate().toByteArray(),"gbk");

        updatedOcrData.setTruck_license_plate(updated_ocr_data_truck_license_plate);


        int updatedUnitsLength=ocrDataProTo.getBody().getUpdatedOcrData().getUnitArrCount();
        List<UpdatedOcrDataUnit> UpdatedOcrDataUnitList=new ArrayList<>();
        for(int u=0;u<updatedUnitsLength;u++){
            UpdatedOcrDataUnit UpdatedOcrDataUnit =new UpdatedOcrDataUnit();
            int updatedUnitLength=ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getUnitBundleArrCount();
            List<UnitNumber>unitNumberList=new ArrayList<>();
            for(int f=0;f<updatedUnitLength;f++){
                UnitNumber unitNumber =new UnitNumber();
                String unit_number=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getUnitBundleArr(f).getUnitNumber().toByteArray(),"gbk");
                String bundle_master =new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getUnitBundleArr(f).getBundleMaster().toByteArray(),"gbk");
                unitNumber.setUnit_number(unit_number);
                unitNumber.setBundle_master(bundle_master);
                unitNumberList.add(unitNumber);
                UpdatedOcrDataUnit.setUnitNumberList(unitNumberList);
            }
            String iso_code=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getIsoCode().toByteArray(),"gbk");
            String position=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getPosition().toByteArray(),"gbk");
            String door_direction=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getDoorDirection().toByteArray(),"gbk");
            String clip_on=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getClipOn().toByteArray(),"gbk");
            String tank_rail=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getTankRail().toByteArray(),"gbk");
            String seal_presence=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getSealPresence().toByteArray(),"gbk");
            String e_seal_state=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getESealState().toByteArray(),"gbk");
            String overheight=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getOverheight().toByteArray(),"gbk");
            String dg_presence=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getDgPresence().toByteArray(),"gbk");
            String dg_class=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getDgClass().toByteArray(),"gbk");
            String flatrack_header_down=new String(ocrDataProTo.getBody().getUpdatedOcrData().getUnitArr(u).getFlatrackHeaderDown().toByteArray(),"gbk");
            UpdatedOcrDataUnit.setIso_code(iso_code);
            UpdatedOcrDataUnit.setPosition(position);
            UpdatedOcrDataUnit.setDoor_direction(door_direction);
            UpdatedOcrDataUnit.setClip_on(clip_on);
            UpdatedOcrDataUnit.setTank_rail(tank_rail);
            UpdatedOcrDataUnit.setSeal_presence(seal_presence);
            UpdatedOcrDataUnit.setE_seal_state(e_seal_state);
            UpdatedOcrDataUnit.setOverheight(overheight);
            UpdatedOcrDataUnit.setDg_presence(dg_presence);
            List<String> stringList1= new ArrayList<>();
            String[]  strs1=dg_class.split(",");
            for(int i=0,len=strs.length;i<len;i++){
                stringList.add(strs1[i].toString());
            }
            UpdatedOcrDataUnit.setDg_class(stringList1);
            UpdatedOcrDataUnit.setFlatrack_header_down(flatrack_header_down);
            UpdatedOcrDataUnitList.add(UpdatedOcrDataUnit);
        }
        UpdatedOcrDataUnits.setUnit(UpdatedOcrDataUnitList);
        updatedOcrData.setUnits(UpdatedOcrDataUnits);
        ocrDataModel.setUpdated_ocr_data(updatedOcrData);



        ocrData.setHeader(header);
        ocrData.setBody(ocrDataModel);
        logger.info("ocrData 转换成javaBean"+ocrData);
        return  ocrData;
    }


    /**
     * 验伤接口转google proto 格式
     * @param damageInspection
     * @param gosAny1
     * @return
     * @throws InvalidProtocolBufferException
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    private Gos.GOSAny toDamageInspectionResponse(DamageInspection damageInspection,Gos.GOSAny gosAny1) throws InvalidProtocolBufferException, UnsupportedEncodingException, JAXBException {
        // 第三方返回的model
        DamageInspectionResponse  damageInspectionResponse= null;

        logger.info("调用DamageInspection第三方接口");
        // 调用第三方接口 Service
        UniResult u= damageInspectionService.toWebService(damageInspection);
        // 判断是否正常返回
        if (Status.OK.equals(u.getCode()) ){
            // 获取到返回的 结果转换成JavaBean
            damageInspectionResponse= (DamageInspectionResponse) u.getDataObject();

        }else {
            damageInspectionResponse= (DamageInspectionResponse) u.getDataObject();

        }
        // 拼装用于zmq消息的model
        Gos.DamageInspection.Builder damageInspectionBuilder= Gos.DamageInspection.newBuilder();

        Gos.Header.Builder header =Gos.Header.newBuilder();

        Gos.Error.Builder errorder= Gos.Error.newBuilder();
        // 从第三方返回的model里获取到 参数 拼装到用于zmq通信的消息中
        if(damageInspectionResponse!=null){
            if(damageInspectionResponse.getHeader()!=null){
                if(damageInspectionResponse.getHeader().getMessageType()!=null){header.setMessageType(ByteString.copyFrom(damageInspectionResponse.getHeader().getMessageType().getBytes("gbk")));}
                if(damageInspectionResponse.getHeader().getMessageID()!=null){header.setMessageID(ByteString.copyFrom(damageInspectionResponse.getHeader().getMessageID().getBytes("gbk")));}
                if(damageInspectionResponse.getHeader().getTimestamp()!=null){header.setTimestamp(ByteString.copyFrom(damageInspectionResponse.getHeader().getTimestamp().getBytes("gbk")));}
                if(damageInspectionResponse.getHeader().getTransaction()!=null){header.setTransaction(ByteString.copyFrom(damageInspectionResponse.getHeader().getTransaction().getBytes("gbk")));}
                if(damageInspectionResponse.getHeader().getVisitID()!=null){header.setVisitID(ByteString.copyFrom(damageInspectionResponse.getHeader().getVisitID().getBytes("gbk")));}
                if(damageInspectionResponse.getHeader().getLane()!=null){header.setLane(ByteString.copyFrom(damageInspectionResponse.getHeader().getLane().getBytes("gbk")));}
                if(damageInspectionResponse.getHeader().getError()!=null){
                    if(damageInspectionResponse.getHeader().getError().getError_code()!=0){errorder.setErrorCode(damageInspectionResponse.getHeader().getError().getError_code());}
                    if(damageInspectionResponse.getHeader().getError().getError_message()!=null){errorder.setErrorMessage(ByteString.copyFrom(damageInspectionResponse.getHeader().getError().getError_message().getBytes("gbk")));}

                }
            }
            header.addErrArr(errorder);
            damageInspectionBuilder.setHeader(header);

        }else {
            logger.info("第三方没有返回数据"+damageInspectionResponse);
        }

        Gos.GOSAny gosAny =Gos.GOSAny.newBuilder().setMsgType(Gos.MSG_TYPE.MSG_DAMAGEINSPECTION).setVisitId(gosAny1.getVisitId()).setModuleName(gosAny1.getModuleName())
                  .setID(1)
                    .setAny(Any.pack(damageInspectionBuilder.build()))
                    .build();
        logger.info("转换成gooler proto格式"+gosAny);
        return gosAny;
    }

    /**
     * 验伤接口 google proto请求转换成javaBean
     * @param gosAny
     * @return
     * @throws InvalidProtocolBufferException
     * @throws UnsupportedEncodingException
     */
    private DamageInspection toDamageInspection(Gos.GOSAny gosAny) throws InvalidProtocolBufferException, UnsupportedEncodingException {
                                 // 属性
                         // Header
        // DamageInspection
                         // DamageInspection
                                            // Untisone 属性是list<UntisTwo>
                                                       // UntisTwo

        // 验伤接口 DamageInspection model
        DamageInspection damageInspectionNew =new DamageInspection();
        // 验伤接口 DamageInspection model的header属性对象
        Header header=new Header();
        // 验伤接口 DamageInspection model的header属性对象
        DamageInspectionModel damageInspectionModel =new DamageInspectionModel();
        // 验伤接口 DamageInspectionModel model的header属性对象
        Untisone untisone=new Untisone();
        // 获取zmq传过来的对象
        Gos.DamageInspection damageInspection=gosAny.getAny().unpack(Gos.DamageInspection.class);
        // 取出对应参数
        String messageID= new String(damageInspection.getHeader().getMessageID().toByteArray(), "gbk");
        String messageType= new String(damageInspection.getHeader().getMessageType().toByteArray(), "gbk");
        String timestamp= new String(damageInspection.getHeader().getTimestamp().toByteArray(), "gbk");
        String transaction= new String(damageInspection.getHeader().getTransaction().toByteArray(), "gbk");
        String visitID= new String(damageInspection.getHeader().getVisitID().toByteArray(), "gbk");
        String lane= new String(damageInspection.getHeader().getLane().toByteArray(), "gbk");

        // 往header 对象里拼装参数
        header.setTransaction(transaction);
        header.setVisitID(visitID);
        header.setMessageType(messageType);
        header.setLane(lane);
        header.setTimestamp(timestamp);
        header.setMessageID(messageID);

        String  operator_id  = new String(damageInspection.getBody().getOperatorId().toByteArray(), "gbk");
        // Untisone对象里装的是 List<UntisTwo>
        List<UntisTwo> unit =new ArrayList<>();
        // 获取 出过来的参数数组长度
        int r=damageInspection.getBody().getUnitArrList().size();
        // 遍历数组
        for(int t=0;t<r;t++){
            // 验伤接口 Untisone model的header属性对象 循环遍历拼接参数
            UntisTwo untisTwo=new UntisTwo();
            String unit_number= new String(damageInspection.getBody().getUnitArr(t).getUnitNumber().toByteArray(),"gbk");
            String has_damage= new String(damageInspection.getBody().getUnitArr(t).getHasDamage().toByteArray(),"gbk");
            String damage_location=new String(damageInspection.getBody().getUnitArr(t).getDamageLocation().toByteArray(),"gbk");
            String damage_type=new String(damageInspection.getBody().getUnitArr(t).getDamageType().toByteArray(),"gbk");
            String damage_description=new String(damageInspection.getBody().getUnitArr(t).getDamageDescription().toByteArray(),"gbk");
            String damage_level=new String(damageInspection.getBody().getUnitArr(t).getDamageLevel().toByteArray(),"gbk");
            untisTwo.setUnit_number(unit_number);
            untisTwo.setHas_damage(has_damage);
            untisTwo.setDamage_location(damage_location);
            untisTwo.setDamage_type(damage_type);
            untisTwo.setDamage_description(damage_description);
            untisTwo.setDamage_level(damage_level);

            unit.add(untisTwo);
        }
        // 拼接参数
        untisone.setOperator_id(operator_id);
        untisone.setUnit(unit);
        damageInspectionModel.setUnits(untisone);
        damageInspectionNew.setHeader(header);
        damageInspectionNew.setBody(damageInspectionModel);

        logger.info("DamageInspection对象生成"+damageInspectionNew.toString());



        // 返回model 对象
        return damageInspectionNew;

    }
}
