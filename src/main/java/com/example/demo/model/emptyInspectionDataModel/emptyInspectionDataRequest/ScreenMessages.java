package com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ScreenMessages  implements Serializable {
    private static final long serialVersionUID = 3153661820457760282L;
    private String confirmation_button;

    private int screen_message_code;

    private String screen_message_text;

    public String getConfirmation_button() {
        return confirmation_button;
    }

    public void setConfirmation_button(String confirmation_button) {
        this.confirmation_button = confirmation_button;
    }

    public int getScreen_message_code() {
        return screen_message_code;
    }

    public void setScreen_message_code(int screen_message_code) {
        this.screen_message_code = screen_message_code;
    }

    public String getScreen_message_text() {
        return screen_message_text;
    }

    public void setScreen_message_text(String screen_message_text) {
        this.screen_message_text = screen_message_text;
    }

    @Override
    public String toString() {
        return "ScreenMessages{" +
                "confirmation_button='" + confirmation_button + '\'' +
                ", screen_message_code=" + screen_message_code +
                ", screen_message_text='" + screen_message_text + '\'' +
                '}';
    }
}
