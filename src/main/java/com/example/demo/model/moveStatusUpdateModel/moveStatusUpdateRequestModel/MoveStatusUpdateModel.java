package com.example.demo.model.moveStatusUpdateModel.moveStatusUpdateRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class MoveStatusUpdateModel  implements Serializable {
    private static final long serialVersionUID = 8045655210729647245L;
    private ScreenMessage screen_message;
    private String process_end;


    public ScreenMessage getScreen_message() {
        return screen_message;
    }

    public void setScreen_message(ScreenMessage screen_message) {
        this.screen_message = screen_message;
    }

    public String getProcess_end() {
        return process_end;
    }

    public void setProcess_end(String process_end) {
        this.process_end = process_end;
    }

    @Override
    public String toString() {
        return "MoveStatusUpdateModel{" +
                "screen_message=" + screen_message +
                ", process_end='" + process_end + '\'' +
                '}';
    }
}
