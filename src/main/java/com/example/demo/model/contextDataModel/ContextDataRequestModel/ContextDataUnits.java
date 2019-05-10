package com.example.demo.model.contextDataModel.ContextDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ContextDataUnits  implements Serializable {
    private static final long serialVersionUID = -8789902355553446120L;
    private List<ContextDataUnitsUnit> unit;

    public List<ContextDataUnitsUnit> getUnit() {
        return unit;
    }

    public void setUnit(List<ContextDataUnitsUnit> unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ContextDataUnits{" +
                "unit=" + unit +
                '}';
    }
}
