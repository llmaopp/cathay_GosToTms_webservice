package com.example.demo.model.transferDataModel.transferDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TransferDataManualCheck implements Serializable {
    private static final long serialVersionUID = 784513875025134906L;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TransferDataManualCheck{" +
                "message='" + message + '\'' +
                '}';
    }
}
