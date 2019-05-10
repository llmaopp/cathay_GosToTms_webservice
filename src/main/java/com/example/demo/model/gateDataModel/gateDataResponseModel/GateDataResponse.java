package com.example.demo.model.gateDataModel.gateDataResponseModel;

import com.example.demo.model.commodel.Header;
import com.example.demo.model.gateDataModel.gateDataRequestModel.GateDataModel;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class GateDataResponse implements Serializable {
    private static final long serialVersionUID = 709000653592135135L;
    private Header header;
    private GateDataModel body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public GateDataModel getBody() {
        return body;
    }

    public void setBody(GateDataModel body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "GateDataResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
