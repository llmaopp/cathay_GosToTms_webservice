package com.example.demo.model.damageInspectionModel.damageInspectionResponse;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/18.
 */
@XmlRootElement
@EntityScan
public class DamageInspectionResponse implements Serializable {
    private static final long serialVersionUID = 4792640250326186707L;
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "DamageInspectionResponse{" +
                "header=" + header +
                '}';
    }
}
