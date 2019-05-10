package com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class EmptyInspectionData  implements Serializable {
    private static final long serialVersionUID = -154484861652497230L;
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
        return "EmptyInspectionData{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
