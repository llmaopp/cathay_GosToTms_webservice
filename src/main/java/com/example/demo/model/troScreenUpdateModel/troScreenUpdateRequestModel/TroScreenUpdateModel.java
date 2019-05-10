package com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TroScreenUpdateModel  implements Serializable {
    private static final long serialVersionUID = -2019689738593462152L;
    private Columnse  columns;
    private Rowse rows;

    public Columnse getColumns() {
        return columns;
    }

    public void setColumns(Columnse columns) {
        this.columns = columns;
    }

    public Rowse getRows() {
        return rows;
    }

    public void setRows(Rowse rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "TroScreenUpdateModel{" +
                "columns=" + columns +
                ", rows=" + rows +
                '}';
    }
}
