package com.example.demo.model.moveStatusUpdateModel.moveStatusUpdateRequestModel;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class MoveStatusUpdate implements Serializable {
    private static final long serialVersionUID = -1210411649622094605L;
    private Header header;

    private MoveStatusUpdateModel body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MoveStatusUpdateModel getBody() {
        return body;
    }

    public void setBody(MoveStatusUpdateModel body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MoveStatusUpdate{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
