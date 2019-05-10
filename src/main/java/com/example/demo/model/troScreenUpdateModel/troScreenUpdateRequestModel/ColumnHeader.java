package com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ColumnHeader implements Serializable {
    private static final long serialVersionUID = 3089427951137962933L;
    private int sequence;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "ColumnHeader{" +
                "sequence=" + sequence +
                '}';
    }
}
