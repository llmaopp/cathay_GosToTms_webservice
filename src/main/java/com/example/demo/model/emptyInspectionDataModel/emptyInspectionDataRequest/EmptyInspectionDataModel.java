package com.example.demo.model.emptyInspectionDataModel.emptyInspectionDataRequest;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class EmptyInspectionDataModel  implements Serializable {
    private static final long serialVersionUID = -203221823766960287L;
    private List<String> rfid_license_plate_tag;
    private EmptyInspectionDataModelUnits units;
    private ManualCheck manualCheck;
    private ScreenMessages screen_message;
    private String open_barrier;

    public List<String> getRfid_license_plate_tag() {
        return rfid_license_plate_tag;
    }

    public void setRfid_license_plate_tag(List<String> rfid_license_plate_tag) {
        this.rfid_license_plate_tag = rfid_license_plate_tag;
    }

    public EmptyInspectionDataModelUnits getUnits() {
        return units;
    }

    public void setUnits(EmptyInspectionDataModelUnits units) {
        this.units = units;
    }

    public ManualCheck getManualCheck() {
        return manualCheck;
    }

    public void setManualCheck(ManualCheck manualCheck) {
        this.manualCheck = manualCheck;
    }

    public ScreenMessages getScreen_message() {
        return screen_message;
    }

    public void setScreen_message(ScreenMessages screen_message) {
        this.screen_message = screen_message;
    }

    public String getOpen_barrier() {
        return open_barrier;
    }

    public void setOpen_barrier(String open_barrier) {
        this.open_barrier = open_barrier;
    }

    @Override
    public String toString() {
        return "EmptyInspectionDataModel{" +
                "rfid_license_plate_tag=" + rfid_license_plate_tag +
                ", units=" + units +
                ", manualCheck=" + manualCheck +
                ", screen_message=" + screen_message +
                ", open_barrier='" + open_barrier + '\'' +
                '}';
    }
}
