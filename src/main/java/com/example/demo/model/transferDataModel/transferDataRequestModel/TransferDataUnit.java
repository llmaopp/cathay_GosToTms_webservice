package com.example.demo.model.transferDataModel.transferDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TransferDataUnit implements Serializable {
    private static final long serialVersionUID = 8462978594619077521L;
    private List<TransferDataUnitNumber> unit_number;
    private String iso_code;
    private String position;
    private String door_direction;

    public List<TransferDataUnitNumber> getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(List<TransferDataUnitNumber> unit_number) {
        this.unit_number = unit_number;
    }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDoor_direction() {
        return door_direction;
    }

    public void setDoor_direction(String door_direction) {
        this.door_direction = door_direction;
    }

    @Override
    public String toString() {
        return "TransferDataUnit{" +
                "unit_number=" + unit_number +
                ", iso_code='" + iso_code + '\'' +
                ", position='" + position + '\'' +
                ", door_direction='" + door_direction + '\'' +
                '}';
    }
}
