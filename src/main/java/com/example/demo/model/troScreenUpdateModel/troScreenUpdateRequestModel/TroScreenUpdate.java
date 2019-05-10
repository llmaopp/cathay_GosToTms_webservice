package com.example.demo.model.troScreenUpdateModel.troScreenUpdateRequestModel;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */

@XmlRootElement
@EntityScan
public class TroScreenUpdate  implements Serializable {
    private static final long serialVersionUID = 1126847206128947180L;
    private Header header;
    private TroScreenUpdateModel body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public TroScreenUpdateModel getBody() {
        return body;
    }

    public void setBody(TroScreenUpdateModel body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "TroScreenUpdate{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
