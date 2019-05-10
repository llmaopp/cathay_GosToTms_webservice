package com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class Rowse implements Serializable {
    private static final long serialVersionUID = 4230846880926501338L;
    private Rowas row;

    public Rowas getRow() {
        return row;
    }

    public void setRow(Rowas row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Rowse{" +
                "row=" + row +
                '}';
    }
}
