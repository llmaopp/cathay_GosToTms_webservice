package com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ManualCheck  implements Serializable {
    private static final long serialVersionUID = -6423223431788800905L;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ManualCheck{" +
                "message='" + message + '\'' +
                '}';
    }
}
