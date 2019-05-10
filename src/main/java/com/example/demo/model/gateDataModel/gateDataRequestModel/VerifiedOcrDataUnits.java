package com.example.demo.model.gateDataModel.gateDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class VerifiedOcrDataUnits implements Serializable {
    private static final long serialVersionUID = -6467821116289659485L;
    private VerifiedOcrDataUnitsUnit unit;

    public VerifiedOcrDataUnitsUnit getUnit() {
        return unit;
    }

    public void setUnit(VerifiedOcrDataUnitsUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "VerifiedOcrDataUnits{" +
                "unit=" + unit +
                '}';
    }
}
