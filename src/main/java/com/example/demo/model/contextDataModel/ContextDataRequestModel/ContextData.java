package com.example.demo.model.contextDataModel.ContextDataRequestModel;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ContextData implements Serializable {

    private static final long serialVersionUID = -5331269526675741616L;
    private Header header;

    private ContextDataModel body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ContextDataModel getBody() {
        return body;
    }

    public void setBody(ContextDataModel body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ContextData{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
