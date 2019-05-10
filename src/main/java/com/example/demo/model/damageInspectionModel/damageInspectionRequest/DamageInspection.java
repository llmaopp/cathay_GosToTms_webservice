package com.example.demo.model.damageInspectionModel.damageInspectionRequest;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/18.
 */
@XmlRootElement
@EntityScan
public class DamageInspection implements Serializable {
    private static final long serialVersionUID = -6720047763470706963L;
    private DamageInspectionModel body;
    private Header header;

    public DamageInspectionModel getBody() {
        return body;
    }

    public void setBody(DamageInspectionModel body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "DamageInspection{" +
                "body=" + body +
                ", header=" + header +
                '}';
    }
}
