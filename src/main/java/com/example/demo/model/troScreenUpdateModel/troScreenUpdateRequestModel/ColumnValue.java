package com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ColumnValue implements Serializable {
    private static final long serialVersionUID = 2826742403212869642L;
    private int sequence;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "ColumnValue{" +
                "sequence=" + sequence +
                '}';
    }
}
