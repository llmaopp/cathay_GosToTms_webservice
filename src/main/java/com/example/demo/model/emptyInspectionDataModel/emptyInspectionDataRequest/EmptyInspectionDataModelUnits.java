package com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class EmptyInspectionDataModelUnits  implements Serializable {
    private static final long serialVersionUID = 2856004728904706531L;
    private List<EmptyInspectionDataModelUnit> unit;

    public List<EmptyInspectionDataModelUnit> getUnit() {
        return unit;
    }

    public void setUnit(List<EmptyInspectionDataModelUnit> unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "EmptyInspectionDataModelUnits{" +
                "unit=" + unit +
                '}';
    }
}
