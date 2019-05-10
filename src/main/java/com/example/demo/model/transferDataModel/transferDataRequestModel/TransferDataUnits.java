package com.example.demo.model.transferDataModel.transferDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
    public class TransferDataUnits implements Serializable {
    private static final long serialVersionUID = 5430424268119325165L;
    private  TransferDataUnit unit;

    public TransferDataUnit getUnit() {
        return unit;
    }

    public void setUnit(TransferDataUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "TransferDataUnits{" +
                "unit=" + unit +
                '}';
    }
}
