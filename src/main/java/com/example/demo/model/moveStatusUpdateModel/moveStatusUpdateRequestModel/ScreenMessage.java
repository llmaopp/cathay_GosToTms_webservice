package com.example.demo.model.moveStatusUpdateModel.moveStatusUpdateRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class ScreenMessage implements Serializable {
    private static final long serialVersionUID = -5377323035109953476L;
    private String confirmation_button;

    private int screen_message_code;

    private int screen_message_text;

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

    public int getScreen_message_text() {
        return screen_message_text;
    }

    public void setScreen_message_text(int screen_message_text) {
        this.screen_message_text = screen_message_text;
    }

    @Override
    public String toString() {
        return "ScreenMessage{" +
                "confirmation_button='" + confirmation_button + '\'' +
                ", screen_message_code=" + screen_message_code +
                ", screen_message_text=" + screen_message_text +
                '}';
    }
}
