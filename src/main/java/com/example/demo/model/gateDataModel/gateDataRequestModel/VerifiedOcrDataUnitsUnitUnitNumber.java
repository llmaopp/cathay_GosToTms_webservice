package com.example.demo.model.gateDataModel.gateDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class VerifiedOcrDataUnitsUnitUnitNumber implements Serializable {
    private static final long serialVersionUID = -5836095524357385447L;
    private String bundle_master;
    private String unit_number;

    public String getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(String unit_number) {
        this.unit_number = unit_number;
    }

    public String getBundle_master() {
        return bundle_master;
    }

    public void setBundle_master(String bundle_master) {
        this.bundle_master = bundle_master;
    }

    @Override
    public String toString() {
        return "VerifiedOcrDataUnitsUnitUnitNumber{" +
                "bundle_master='" + bundle_master + '\'' +
                ", unit_number='" + unit_number + '\'' +
                '}';
    }
}
