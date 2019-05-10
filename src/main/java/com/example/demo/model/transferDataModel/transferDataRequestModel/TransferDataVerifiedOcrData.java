package com.example.demo.model.transferDataModel.transferDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TransferDataVerifiedOcrData implements Serializable {
    private static final long serialVersionUID = -3453213123306329463L;
    private TransferDataUnits units;

    public TransferDataUnits getUnits() {
        return units;
    }

    public void setUnits(TransferDataUnits units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "TransferDataVerifiedOcrData{" +
                "units=" + units +
                '}';
    }
}
