package com.example.demo.model.transferDataModel.transferDataRequestModel;



import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class TransferDataModel implements Serializable {
    private static final long serialVersionUID = -4260504225813369474L;
    private List<String> rfid_license_plate_tag;
    private String truck_license_plate;
    private int driver_card_number;
    private TransferDataVerifiedOcrData verified_ocr_data;
    private String pick_up_requested;
    private String pick_up_position;
    private String indicate_pick_up_action;
    private String indicate_pick_up_position;
    private TransferDataScreenMessage screen_message;
    private TransferDataManualCheck manual_check;
    private String open_barrier;
    private String process_end;
    private String deny_entry;
    private String deny_exit;

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

    public int getDriver_card_number() {
        return driver_card_number;
    }

    public void setDriver_card_number(int driver_card_number) {
        this.driver_card_number = driver_card_number;
    }

    public TransferDataVerifiedOcrData getVerified_ocr_data() {
        return verified_ocr_data;
    }

    public void setVerified_ocr_data(TransferDataVerifiedOcrData verified_ocr_data) {
        this.verified_ocr_data = verified_ocr_data;
    }

    public String getPick_up_requested() {
        return pick_up_requested;
    }

    public void setPick_up_requested(String pick_up_requested) {
        this.pick_up_requested = pick_up_requested;
    }

    public String getPick_up_position() {
        return pick_up_position;
    }

    public void setPick_up_position(String pick_up_position) {
        this.pick_up_position = pick_up_position;
    }

    public String getIndicate_pick_up_action() {
        return indicate_pick_up_action;
    }

    public void setIndicate_pick_up_action(String indicate_pick_up_action) {
        this.indicate_pick_up_action = indicate_pick_up_action;
    }

    public String getIndicate_pick_up_position() {
        return indicate_pick_up_position;
    }

    public void setIndicate_pick_up_position(String indicate_pick_up_position) {
        this.indicate_pick_up_position = indicate_pick_up_position;
    }

    public TransferDataScreenMessage getScreen_message() {
        return screen_message;
    }

    public void setScreen_message(TransferDataScreenMessage screen_message) {
        this.screen_message = screen_message;
    }

    public TransferDataManualCheck getManual_check() {
        return manual_check;
    }

    public void setManual_check(TransferDataManualCheck manual_check) {
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

    public String getDeny_entry() {
        return deny_entry;
    }

    public void setDeny_entry(String deny_entry) {
        this.deny_entry = deny_entry;
    }

    public String getDeny_exit() {
        return deny_exit;
    }

    public void setDeny_exit(String deny_exit) {
        this.deny_exit = deny_exit;
    }

    @Override
    public String toString() {
        return "TransferDataModel{" +
                "rfid_license_plate_tag=" + rfid_license_plate_tag +
                ", truck_license_plate='" + truck_license_plate + '\'' +
                ", driver_card_number=" + driver_card_number +
                ", verified_ocr_data=" + verified_ocr_data +
                ", pick_up_requested='" + pick_up_requested + '\'' +
                ", pick_up_position='" + pick_up_position + '\'' +
                ", indicate_pick_up_action='" + indicate_pick_up_action + '\'' +
                ", indicate_pick_up_position='" + indicate_pick_up_position + '\'' +
                ", screen_message=" + screen_message +
                ", manual_check=" + manual_check +
                ", open_barrier='" + open_barrier + '\'' +
                ", process_end='" + process_end + '\'' +
                ", deny_entry='" + deny_entry + '\'' +
                ", deny_exit='" + deny_exit + '\'' +
                '}';
    }
}
