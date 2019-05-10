package com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class Rowas implements Serializable {

    private static final long serialVersionUID = -348302333035826387L;
    private List<ColumnValue> column_value;

    public List<ColumnValue> getColumn_value() {
        return column_value;
    }

    public void setColumn_value(List<ColumnValue> column_value) {
        this.column_value = column_value;
    }

    @Override
    public String toString() {
        return "Rowas{" +
                "column_value=" + column_value +
                '}';
    }
}
