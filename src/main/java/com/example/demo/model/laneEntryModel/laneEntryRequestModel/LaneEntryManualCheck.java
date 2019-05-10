package com.example.demo.model.laneEntryModel.laneEntryRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class LaneEntryManualCheck implements Serializable {
    private static final long serialVersionUID = -8485093418555934270L;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LaneEntryManualCheck{" +
                "message='" + message + '\'' +
                '}';
    }
}
