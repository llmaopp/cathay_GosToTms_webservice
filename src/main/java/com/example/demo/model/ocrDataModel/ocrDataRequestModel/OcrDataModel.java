package com.example.demo.model.ocrDataModel.ocrDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/23.
 */
@XmlRootElement
@EntityScan
public class OcrDataModel implements Serializable {
    private static final long serialVersionUID = -3992925644012555531L;
    // OCR门户通道时间戳，ISO 8601格式，当地时间，例如2003-02-20T15：53：51。
    private  String passage_timestamp;
    // RFID牌照标签。可以是多个标签。
    private List<String> rfid_license_plate_tag;
    // 原始的、未经运算符修改的OCR数据的父元素
    private OriginalOcrData original_ocr_data;
    // 更新的OCR数据的父元素(由操作符处理)。
    private UpdatedOcrData updated_ocr_data;
    //如果操作员已验证OCR数据，则强制执行。操作符ID(用户名)。
    private String operator_id;
    // 快照位置：完整的Windows文件路径到相机门通道压缩文件。例如：F：\ICG 2\20160714\QQCTN-ICG 2 20160714-040505-61583.zi
    private String snapshot_location;

    public String getPassage_timestamp() {
        return passage_timestamp;
    }

    public void setPassage_timestamp(String passage_timestamp) {
        this.passage_timestamp = passage_timestamp;
    }

    public List<String> getRfid_license_plate_tag() {
        return rfid_license_plate_tag;
    }

    public void setRfid_license_plate_tag(List<String> rfid_license_plate_tag) {
        this.rfid_license_plate_tag = rfid_license_plate_tag;
    }

    public OriginalOcrData getOriginal_ocr_data() {
        return original_ocr_data;
    }

    public void setOriginal_ocr_data(OriginalOcrData original_ocr_data) {
        this.original_ocr_data = original_ocr_data;
    }

    public UpdatedOcrData getUpdated_ocr_data() {
        return updated_ocr_data;
    }

    public void setUpdated_ocr_data(UpdatedOcrData updated_ocr_data) {
        this.updated_ocr_data = updated_ocr_data;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getSnapshot_location() {
        return snapshot_location;
    }

    public void setSnapshot_location(String snapshot_location) {
        this.snapshot_location = snapshot_location;
    }

    @Override
    public String toString() {
        return "OcrDataModel{" +
                "passage_timestamp='" + passage_timestamp + '\'' +
                ", rfid_license_plate_tag=" + rfid_license_plate_tag +
                ", original_ocr_data=" + original_ocr_data +
                ", updated_ocr_data=" + updated_ocr_data +
                ", operator_id='" + operator_id + '\'' +
                ", snapshot_location='" + snapshot_location + '\'' +
                '}';
    }
}
