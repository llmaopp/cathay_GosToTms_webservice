package com.example.demo.model.transferDataModel.transferDataResponseModel;

import com.example.demo.model.commodel.Header;
import com.example.demo.model.transferDataModel.transferDataRequestModel.TransferDataModel;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TransferDataResponse implements Serializable {
    private static final long serialVersionUID = -3372528848704797718L;
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
        return "TransferDataResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
