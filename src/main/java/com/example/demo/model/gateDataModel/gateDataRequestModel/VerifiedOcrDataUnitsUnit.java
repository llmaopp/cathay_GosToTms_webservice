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
public class VerifiedOcrDataUnitsUnit implements Serializable {
    private static final long serialVersionUID = 6624357875368685195L;
    private List<VerifiedOcrDataUnitsUnitUnitNumber> verifiedOcrDataUnitsUnitUnitNumber;
    private String iso_code;
    private String position;
    private String door_direction;
    private String clip_on;
    private String tank_rail;
    private String seal_presence;
    private String e_seal_state;
    private String overheight;
    private String dg_presence;
    private String dg_class;
    private String flatrack_header_down;

    public List<VerifiedOcrDataUnitsUnitUnitNumber> getVerifiedOcrDataUnitsUnitUnitNumber() {
        return verifiedOcrDataUnitsUnitUnitNumber;
    }

    public void setVerifiedOcrDataUnitsUnitUnitNumber(List<VerifiedOcrDataUnitsUnitUnitNumber> verifiedOcrDataUnitsUnitUnitNumber) {
        this.verifiedOcrDataUnitsUnitUnitNumber = verifiedOcrDataUnitsUnitUnitNumber;
    }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDoor_direction() {
        return door_direction;
    }

    public void setDoor_direction(String door_direction) {
        this.door_direction = door_direction;
    }

    public String getClip_on() {
        return clip_on;
    }

    public void setClip_on(String clip_on) {
        this.clip_on = clip_on;
    }

    public String getTank_rail() {
        return tank_rail;
    }

    public void setTank_rail(String tank_rail) {
        this.tank_rail = tank_rail;
    }

    public String getSeal_presence() {
        return seal_presence;
    }

    public void setSeal_presence(String seal_presence) {
        this.seal_presence = seal_presence;
    }

    public String getE_seal_state() {
        return e_seal_state;
    }

    public void setE_seal_state(String e_seal_state) {
        this.e_seal_state = e_seal_state;
    }

    public String getOverheight() {
        return overheight;
    }

    public void setOverheight(String overheight) {
        this.overheight = overheight;
    }

    public String getDg_presence() {
        return dg_presence;
    }

    public void setDg_presence(String dg_presence) {
        this.dg_presence = dg_presence;
    }

    public String getDg_class() {
        return dg_class;
    }

    public void setDg_class(String dg_class) {
        this.dg_class = dg_class;
    }

    public String getFlatrack_header_down() {
        return flatrack_header_down;
    }

    public void setFlatrack_header_down(String flatrack_header_down) {
        this.flatrack_header_down = flatrack_header_down;
    }

    @Override
    public String toString() {
        return "VerifiedOcrDataUnitsUnit{" +
                "verifiedOcrDataUnitsUnitUnitNumber=" + verifiedOcrDataUnitsUnitUnitNumber +
                ", iso_code='" + iso_code + '\'' +
                ", position='" + position + '\'' +
                ", door_direction='" + door_direction + '\'' +
                ", clip_on='" + clip_on + '\'' +
                ", tank_rail='" + tank_rail + '\'' +
                ", seal_presence='" + seal_presence + '\'' +
                ", e_seal_state='" + e_seal_state + '\'' +
                ", overheight='" + overheight + '\'' +
                ", dg_presence='" + dg_presence + '\'' +
                ", dg_class='" + dg_class + '\'' +
                ", flatrack_header_down='" + flatrack_header_down + '\'' +
                '}';
    }
}
