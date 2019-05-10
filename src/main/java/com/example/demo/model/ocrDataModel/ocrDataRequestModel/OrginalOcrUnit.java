package com.example.demo.model.ocrDataModel.ocrDataRequestModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cathay on 2019/4/23.
 */
@XmlRootElement
@EntityScan
public class OrginalOcrUnit implements Serializable {

    private static final long serialVersionUID = -4630912548584385066L;
    // 单位号码。在捆绑的情况下，没有单位号码或只有一个单位号码的平面架将被识别。
    private List<String> unit_number;
    // ISO代码。在捆绑的情况下：ISO代码的母版。
    private String  iso_code;
    //底盘上的单位位置：F(前)M(中)A(船尾)
    private String position;
    // 门的方向：F(前)B(后)U(未知-例如在坦克或包裹的情况下)
    private String door_direction;
    // 如果启用了剪辑检查，则强制执行。剪辑-on(外部集)存在：y(剪辑-on当前)N(无剪辑-on当前)
    private String clip_on;
    // 如果启用了油箱检查，则强制执行。罐体钢轨存在：顶部(仅存在于顶部的钢轨)底部(仅存在于底部的)顶部-底部(存在于顶部和底部的)无(没有顶部和底部的钢轨)
    private String tank_rail;
    // 密封存在：Y(密封存在)N(没有密封存在)U(密封存在不可检测/门不可见)
    private String seal_presence;

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

    public List<String> getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(List<String> unit_number) {
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

    public String getSeal_presence() {
        return seal_presence;
    }

    public void setSeal_presence(String seal_presence) {
        this.seal_presence = seal_presence;
    }

    @Override
    public String toString() {
        return "OrginalOcrUnit{" +
                "unit_number=" + unit_number +
                ", iso_code='" + iso_code + '\'' +
                ", position='" + position + '\'' +
                ", door_direction='" + door_direction + '\'' +
                ", clip_on='" + clip_on + '\'' +
                ", tank_rail='" + tank_rail + '\'' +
                ", seal_presence='" + seal_presence + '\'' +
                '}';
    }
}
