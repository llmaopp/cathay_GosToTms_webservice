package com.example.demo.model.transferDataModel.transferDataRequestModel;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TransferData implements Serializable {
    private static final long serialVersionUID = 4176647691775211682L;
    private Header header;
    private TransferDataModel body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public TransferDataModel getBody() {
        return body;
    }

    public void setBody(TransferDataModel body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "TransferData{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
