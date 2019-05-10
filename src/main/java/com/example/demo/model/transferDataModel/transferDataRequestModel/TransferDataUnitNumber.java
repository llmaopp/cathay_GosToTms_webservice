package com.example.demo.model.transferDataModel.transferDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TransferDataUnitNumber implements Serializable {
    private static final long serialVersionUID = 808423648419063285L;
    private String bundle_master;

    public String getBundle_master() {
        return bundle_master;
    }

    public void setBundle_master(String bundle_master) {
        this.bundle_master = bundle_master;
    }

    @Override
    public String toString() {
        return "TransferDataUnitNumber{" +
                "bundle_master='" + bundle_master + '\'' +
                '}';
    }
}
