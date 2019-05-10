package com.example.demo.model.ocrDataModel.ocrDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/26.
 */
@XmlRootElement
@EntityScan
public class UnitNumber implements Serializable {
    private static final long serialVersionUID = 6290881982286298088L;
    private String unit_number;

    private String bundle_master;

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
        return "UnitNumber{" +
                "unit_number='" + unit_number + '\'' +
                ", bundle_master='" + bundle_master + '\'' +
                '}';
    }
}
