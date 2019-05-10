package com.example.demo.model.damageInspectionModel.damageInspectionRequest;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;

/**
 * Created by cathay on 2019/4/18.
 */
@EntityScan
public class DamageInspectionModel implements Serializable {

    private static final long serialVersionUID = 5316828703626914589L;
    private Untisone units;

    public void setUnits(Untisone units) {
        this.units = units;
    }

    public Untisone getUnits() {
        return units;
    }


    @Override
    public String toString() {
        return "DamageInspectionModel{" +
                "units=" + units +
                '}';
    }
}
