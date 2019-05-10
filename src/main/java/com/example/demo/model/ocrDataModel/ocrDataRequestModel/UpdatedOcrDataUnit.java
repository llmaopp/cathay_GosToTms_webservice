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
public class UpdatedOcrDataUnit implements Serializable {
    private static final long serialVersionUID = -4756539230258767035L;
    // 一个11M单元号(=集装箱号或平面机架号)。在捆绑的情况下，将有多个单位数。

    private List<UnitNumber> unitNumberList;
    // ISO代码。在捆绑的情况下：ISO主机代码
    private String iso_code;
    // 底盘上的单位位置：F(前)M(中)A(船尾)
    private String position;
    // 门的方向：F(前)B(后)U(未知-例如在坦克或包裹的情况下)
    private String door_direction;
    // 如果启用了剪辑检查，则强制执行。剪辑-on(外部集)存在：y(剪辑-on当前)N(无剪辑-on当前)
    private String clip_on;
    // 如果启用了油箱检查，则强制执行。罐体钢轨存在：顶部(仅存在于顶部的钢轨)底部(仅存在于底部的)顶部-底部(存在于顶部和底部的)无(没有顶部和底部的钢轨)
    private String tank_rail;
    // 密封存在：Y(密封存在)N(没有密封存在)U(密封存在不可检测/门不可见)
    private String seal_presence;
    // 强制性的，如果容器有电子印章(基于上下文数据)：电子印章状态：确定.位置不对。无e密封
//    Mandatory if container has e-seal (based
//    on context data):
//    E-seal state:
//    OK.
//    wrong_position.
//    no_e_seal
    private String e_seal_state;
    //如果启用了打开顶检查，则强制检查并在开放顶或平面架ISO代码表中启用ISO代码.过高：Y N
    private String overheight;
    //  危险货物标签的存在：Y(危险货物标签)N(没有危险货物标签)
    private String dg_presence;
    // 强制if_g_reence=Y.危险品标签类别。(可能有多个实例。)
    private List<String> dg_class;
    private String flatrack_header_down;


    public List<UnitNumber> getUnitNumberList() {
        return unitNumberList;
    }

    public void setUnitNumberList(List<UnitNumber> unitNumberList) {
        this.unitNumberList = unitNumberList;
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

    public List<String> getDg_class() {
        return dg_class;
    }

    public void setDg_class(List<String> dg_class) {
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
        return "UpdatedOcrDataUnit{" +
                "unitNumberList=" + unitNumberList +
                ", iso_code='" + iso_code + '\'' +
                ", position='" + position + '\'' +
                ", door_direction='" + door_direction + '\'' +
                ", clip_on='" + clip_on + '\'' +
                ", tank_rail='" + tank_rail + '\'' +
                ", seal_presence='" + seal_presence + '\'' +
                ", e_seal_state='" + e_seal_state + '\'' +
                ", overheight='" + overheight + '\'' +
                ", dg_presence='" + dg_presence + '\'' +
                ", dg_class=" + dg_class +
                ", flatrack_header_down='" + flatrack_header_down + '\'' +
                '}';
    }
}
