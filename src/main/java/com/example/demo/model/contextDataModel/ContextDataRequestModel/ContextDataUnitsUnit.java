package com.example.demo.model.contextDataModel.ContextDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ContextDataUnitsUnit  implements Serializable {
    private static final long serialVersionUID = 7985579833925805122L;
    private String unit_number;
    private String iso_code;
    private String e_seal_presence;

    public String getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(String unit_number) {
        this.unit_number = unit_number;
    }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public String getE_seal_presence() {
        return e_seal_presence;
    }

    public void setE_seal_presence(String e_seal_presence) {
        this.e_seal_presence = e_seal_presence;
    }

    @Override
    public String toString() {
        return "ContextDataUnitsUnit{" +
                "unit_number='" + unit_number + '\'' +
                ", iso_code='" + iso_code + '\'' +
                ", e_seal_presence='" + e_seal_presence + '\'' +
                '}';
    }
}
