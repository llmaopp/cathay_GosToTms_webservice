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
public class Columnse implements Serializable {
    private static final long serialVersionUID = 5059255052239325L;
    private int count;
    private List<ColumnHeader> column_header;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Columnse{" +
                "count=" + count +
                ", column_header=" + column_header +
                '}';
    }
}
