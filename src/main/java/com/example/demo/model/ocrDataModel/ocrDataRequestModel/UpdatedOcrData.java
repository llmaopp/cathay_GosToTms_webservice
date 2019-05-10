package com.example.demo.model.ocrDataModel.ocrDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/23.
 */
@XmlRootElement
@EntityScan
public class UpdatedOcrData implements Serializable {
    private static final long serialVersionUID = -3370781817031051062L;
    // 卡车牌照
    private String truck_license_plate;
    // 单元元素的父元素。
    private UpdatedOcrDataUnits units;

    public String getTruck_license_plate() {
        return truck_license_plate;
    }

    public void setTruck_license_plate(String truck_license_plate) {
        this.truck_license_plate = truck_license_plate;
    }

    public UpdatedOcrDataUnits getUnits() {
        return units;
    }

    public void setUnits(UpdatedOcrDataUnits units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "UpdatedOcrData{" +
                "truck_license_plate='" + truck_license_plate + '\'' +
                ", units=" + units +
                '}';
    }
}
