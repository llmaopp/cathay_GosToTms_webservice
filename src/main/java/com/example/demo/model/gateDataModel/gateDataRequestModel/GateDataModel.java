package com.example.demo.model.gateDataModel.gateDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class GateDataModel implements Serializable {
    private static final long serialVersionUID = -3236513356685291581L;
    private List<String>  rfid_license_plate_tag;
    private String truck_license_plate;
    private String driver_card_number;
    private int gross_weight;
    private int cargo_card_number;
    private VerifiedOcrData verified_ocr_data;
    private String  pick_up_position;
    private String seal_number;
    private String  non_containerized_cargo;
    private String indicate_pick_up_position;
    private String enter_seal_number;
    private GateDataScreenMessage screen_message;
    private String print;
    private String print_data;
    private String manual_check;
    private String  message;
    private String open_barrier;
    private String process_end;

    /**
     * OCRgt
     */
    private String isManual;

    public String getIsManual() {
        return isManual;
    }

    public void setIsManual(String isManual) {
        this.isManual = isManual;
    }

    public String getPrint_data() {
        return print_data;
    }

    public void setPrint_data(String print_data) {
        this.print_data = print_data;
    }

    public String getManual_check() {
        return manual_check;
    }

    public void setManual_check(String manual_check) {
        this.manual_check = manual_check;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getDriver_card_number() {
        return driver_card_number;
    }

    public void setDriver_card_number(String driver_card_number) {
        this.driver_card_number = driver_card_number;
    }

    public int getGross_weight() {
        return gross_weight;
    }

    public void setGross_weight(int gross_weight) {
        this.gross_weight = gross_weight;
    }

    public int getCargo_card_number() {
        return cargo_card_number;
    }

    public void setCargo_card_number(int cargo_card_number) {
        this.cargo_card_number = cargo_card_number;
    }

    public VerifiedOcrData getVerified_ocr_data() {
        return verified_ocr_data;
    }

    public void setVerified_ocr_data(VerifiedOcrData verified_ocr_data) {
        this.verified_ocr_data = verified_ocr_data;
    }

    public String getPick_up_position() {
        return pick_up_position;
    }

    public void setPick_up_position(String pick_up_position) {
        this.pick_up_position = pick_up_position;
    }

    public String getSeal_number() {
        return seal_number;
    }

    public void setSeal_number(String seal_number) {
        this.seal_number = seal_number;
    }

    public String getNon_containerized_cargo() {
        return non_containerized_cargo;
    }

    public void setNon_containerized_cargo(String non_containerized_cargo) {
        this.non_containerized_cargo = non_containerized_cargo;
    }

    public String getIndicate_pick_up_position() {
        return indicate_pick_up_position;
    }

    public void setIndicate_pick_up_position(String indicate_pick_up_position) {
        this.indicate_pick_up_position = indicate_pick_up_position;
    }

    public String getEnter_seal_number() {
        return enter_seal_number;
    }

    public void setEnter_seal_number(String enter_seal_number) {
        this.enter_seal_number = enter_seal_number;
    }

    public GateDataScreenMessage getScreen_message() {
        return screen_message;
    }

    public void setScreen_message(GateDataScreenMessage screen_message) {
        this.screen_message = screen_message;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    @Override
    public String toString() {
        return "GateDataModel{" +
                "rfid_license_plate_tag=" + rfid_license_plate_tag +
                ", truck_license_plate='" + truck_license_plate + '\'' +
                ", driver_card_number='" + driver_card_number + '\'' +
                ", gross_weight=" + gross_weight +
                ", cargo_card_number=" + cargo_card_number +
                ", verified_ocr_data=" + verified_ocr_data +
                ", pick_up_position='" + pick_up_position + '\'' +
                ", seal_number='" + seal_number + '\'' +
                ", non_containerized_cargo='" + non_containerized_cargo + '\'' +
                ", indicate_pick_up_position='" + indicate_pick_up_position + '\'' +
                ", enter_seal_number='" + enter_seal_number + '\'' +
                ", screen_message=" + screen_message +
                ", print='" + print + '\'' +
                ", print_data='" + print_data + '\'' +
                ", manual_check='" + manual_check + '\'' +
                ", message='" + message + '\'' +
                ", open_barrier='" + open_barrier + '\'' +
                ", process_end='" + process_end + '\'' +
                ", isManual='" + isManual + '\'' +
                '}';
    }
}
