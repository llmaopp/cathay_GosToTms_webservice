package com.example.demo.model.truckInfoModel.truckInfoRequestModel;

import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
public class Unitses {
    private List<UnitNumber> unit_number;
    private String iso_code;
    private String position;
    private String door_direction;
    private String clip_on;
    private String tank_rail;
    private String seal_presence;
    private String e_seal_state;
    private String overheight;
    private String dg_class;
    private String flatrack_header_down;

    public List<UnitNumber> getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(List<UnitNumber> unit_number) {
        this.unit_number = unit_number;
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
}
