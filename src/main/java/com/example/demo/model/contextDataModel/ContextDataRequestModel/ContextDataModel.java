package com.example.demo.model.contextDataModel.ContextDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ContextDataModel  implements Serializable {
    private static final long serialVersionUID = -6650983738903159586L;
    private String direction;

    private ContextDataUnits units;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public ContextDataUnits getUnits() {
        return units;
    }

    public void setUnits(ContextDataUnits units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "ContextDataModel{" +
                "direction='" + direction + '\'' +
                ", units=" + units +
                '}';
    }
}
