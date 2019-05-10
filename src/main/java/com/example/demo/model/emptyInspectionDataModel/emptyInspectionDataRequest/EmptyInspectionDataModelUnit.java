package com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class EmptyInspectionDataModelUnit  implements Serializable {
    private static final long serialVersionUID = 1374219802608086686L;
    private String unit_number;
    private String empty;
    private String snapshot_location;

    public String getSnapshot_location() {
        return snapshot_location;
    }

    public void setSnapshot_location(String snapshot_location) {
        this.snapshot_location = snapshot_location;
    }

    public String getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(String unit_number) {
        this.unit_number = unit_number;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    @Override
    public String toString() {
        return "EmptyInspectionDataModelUnit{" +
                "unit_number='" + unit_number + '\'' +
                ", empty='" + empty + '\'' +
                ", snapshot_location='" + snapshot_location + '\'' +
                '}';
    }
}
