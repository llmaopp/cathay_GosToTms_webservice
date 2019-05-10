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
public class OriginalOcrData implements Serializable {
    private static final long serialVersionUID = -7054003355731451731L;
    //卡车牌照。
    private String truck_license_plate;
    // 单元元素的父元素
    private List<OrginalOcUnits> units;

    public String getTruck_license_plate() {
        return truck_license_plate;
    }

    public void setTruck_license_plate(String truck_license_plate) {
        this.truck_license_plate = truck_license_plate;
    }

    public List<OrginalOcUnits> getUnits() {
        return units;
    }

    public void setUnits(List<OrginalOcUnits> units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "OriginalOcrData{" +
                "truck_license_plate='" + truck_license_plate + '\'' +
                ", units=" + units +
                '}';
    }
}
