package com.example.demo.model.damageInspectionModel.damageInspectionRequest;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/18.
 */
@EntityScan
public class Untisone  implements Serializable {
    private static final long serialVersionUID = -5702522091619003619L;
    private List<UntisTwo> unit;

    private String operator_id;

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public List<UntisTwo> getUnit() {
        return unit;
    }

    public void setUnit(List<UntisTwo> unit) {
        this.unit = unit;
    }


    @Override
    public String toString() {
        return "Untisone{" +
                "unit=" + unit +
                ", operator_id='" + operator_id + '\'' +
                '}';
    }
}
