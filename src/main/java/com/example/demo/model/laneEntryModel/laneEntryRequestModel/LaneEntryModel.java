package com.example.demo.model.laneEntryModel.laneEntryRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class LaneEntryModel implements Serializable {
    private static final long serialVersionUID = 7716028684844757732L;
    private String process_start_timestamp;
    private List<String> rfid_license_plate_tag;
    private String truck_license_plate;
    private String has_driver_card;
    private int driver_card_number;
    private LaneEntryScreenMessage screen_message;
    private String print;
    private String print_data;
    private LaneEntryManualCheck manual_check;
    private String open_barrier;
    private String process_end;

    public String getProcess_start_timestamp() {
        return process_start_timestamp;
    }

    public void setProcess_start_timestamp(String process_start_timestamp) {
        this.process_start_timestamp = process_start_timestamp;
    }

    public List<String> getRfid_license_plate_tag() {
        return rfid_license_plate_tag;
    }

    public void setRfid_license_plate_tag(List<String> rfid_license_plate_tag) {
        this.rfid_license_plate_tag = rfid_license_plate_tag;
    }

    public String getTruck_license_plate() {
        return truck_license_plate;
    }

    public void setTruck_license_plate(String truck_license_plate) {
        this.truck_license_plate = truck_license_plate;
    }

    public String getHas_driver_card() {
        return has_driver_card;
    }

    public void setHas_driver_card(String has_driver_card) {
        this.has_driver_card = has_driver_card;
    }

    public int getDriver_card_number() {
        return driver_card_number;
    }

    public void setDriver_card_number(int driver_card_number) {
        this.driver_card_number = driver_card_number;
    }

    public LaneEntryScreenMessage getScreen_message() {
        return screen_message;
    }

    public void setScreen_message(LaneEntryScreenMessage screen_message) {
        this.screen_message = screen_message;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getPrint_data() {
        return print_data;
    }

    public void setPrint_data(String print_data) {
        this.print_data = print_data;
    }

    public LaneEntryManualCheck getManual_check() {
        return manual_check;
    }

    public void setManual_check(LaneEntryManualCheck manual_check) {
        this.manual_check = manual_check;
    }

    public String getOpen_barrier() {
        return open_barrier;
    }

    public void setOpen_barrier(String open_barrier) {
        this.open_barrier = open_barrier;
    }

    public String getProcess_end() {
        return process_end;
    }

    public void setProcess_end(String process_end) {
        this.process_end = process_end;
    }

    @Override
    public String toString() {
        return "LaneEntryModel{" +
                "process_start_timestamp='" + process_start_timestamp + '\'' +
                ", rfid_license_plate_tag=" + rfid_license_plate_tag +
                ", truck_license_plate='" + truck_license_plate + '\'' +
                ", has_driver_card='" + has_driver_card + '\'' +
                ", driver_card_number=" + driver_card_number +
                ", screen_message=" + screen_message +
                ", print='" + print + '\'' +
                ", print_data='" + print_data + '\'' +
                ", manual_check=" + manual_check +
                ", open_barrier='" + open_barrier + '\'' +
                ", process_end='" + process_end + '\'' +
                '}';
    }
}
