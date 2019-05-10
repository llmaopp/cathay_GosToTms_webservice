package com.example.demo.model.gateDataModel.gateDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class VerifiedOcrData implements Serializable {
    private static final long serialVersionUID = -1818100988997995146L;
    private VerifiedOcrDataUnits units;

    public VerifiedOcrDataUnits getUnits() {
        return units;
    }

    public void setUnits(VerifiedOcrDataUnits units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "VerifiedOcrData{" +
                "units=" + units +
                '}';
    }
}
