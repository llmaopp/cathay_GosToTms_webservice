package com.example.demo.model.troScreenUpdateModel.troScreenUpdateResponseModel;

import com.example.demo.model.commodel.Header;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TroScreenUpdateResponse implements Serializable {
    private static final long serialVersionUID = 8344239213773585480L;
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "TroScreenUpdateResponse{" +
                "header=" + header +
                '}';
    }
}
