package com.example.demo.model.ocrDataModel.ocrDataRequestModel;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/23.
 */
@XmlRootElement
@EntityScan
public class OcrData implements Serializable {
    private static final long serialVersionUID = 8706489388790232012L;
    private Header header;
    private  OcrDataModel body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public OcrDataModel getBody() {
        return body;
    }

    public void setBody(OcrDataModel body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "OcrData{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
