package com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataResponse;

import com.example.demo.model.commodel.Header;
import com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest.EmptyInspectionDataModel;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class EmptyInspectionDataResponse  implements Serializable {
    private static final long serialVersionUID = 4968580116082395550L;
    private Header header;
    private EmptyInspectionDataModel body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public EmptyInspectionDataModel getBody() {
        return body;
    }

    public void setBody(EmptyInspectionDataModel body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EmptyInspectionDataResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
