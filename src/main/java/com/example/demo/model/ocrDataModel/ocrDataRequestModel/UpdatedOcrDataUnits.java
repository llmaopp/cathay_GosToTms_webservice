package com.example.demo.model.ocrDataModel.ocrDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/23.
 */
@XmlRootElement
@EntityScan
public class UpdatedOcrDataUnits implements Serializable {

    private static final long serialVersionUID = -651895776301251763L;
    // 单元OCR数据的容器。
    private List<UpdatedOcrDataUnit> unit;

    public List<UpdatedOcrDataUnit> getUnit() {
        return unit;
    }

    public void setUnit(List<UpdatedOcrDataUnit> unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "UpdatedOcrDataUnits{" +
                "unit=" + unit +
                '}';
    }
}
