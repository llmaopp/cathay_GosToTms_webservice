package com.example.demo.model.ocrDataModel.ocrDataResponseModel;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/23.
 */
@XmlRootElement
@EntityScan
public class OcrDataResponse implements Serializable {
    private static final long serialVersionUID = 4169668782271726941L;
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "OcrDataResponse{" +
                "header=" + header +
                '}';
    }
}
