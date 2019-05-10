package com.example.demo.model.ocrDataModel.ocrDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/23.
 */
@XmlRootElement
@EntityScan
public class OrginalOcUnits implements Serializable {
    private static final long serialVersionUID = 8955755902550413211L;
    private OrginalOcrUnit unit;

    public OrginalOcrUnit getUnit() {
        return unit;
    }

    public void setUnit(OrginalOcrUnit unit) {
        this.unit = unit;
    }


    @Override
    public String toString() {
        return "OrginalOcUnits{" +
                "unit=" + unit +
                '}';
    }
}
