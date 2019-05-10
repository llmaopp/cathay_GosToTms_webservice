package com.example.demo.model.damageInspectionModel.damageInspectionRequest;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;

/**
 * Created by cathay on 2019/4/18.
 */
@EntityScan
public class UntisTwo implements Serializable {
    private static final long serialVersionUID = 560404698874824541L;
    // 单元数（=集装箱号码）
    private String unit_number;
    // 指示装置是否损坏：Y-装置损坏N-装置没有损坏
    private String has_damage;
    //损坏位置代码。(代码保存在GOS中。)在损坏情况下是强制性的=Y.
    private String damage_location;
    // 损坏类型代码。（代码保存在GOS中。）如果损坏=Y，则必须使用。
    private String damage_type;
    //损坏描述（自由文本）。如果损坏=Y，则必须使用
    private String damage_description;
    //伤害等级：轻微的主要强制性的情况下伤害=Y。
    private String damage_level;
    // 卡车标识
    private String visitID;
    //唯一识别卡车位置。
    private String lane;

    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getDamage_level() {
        return damage_level;
    }

    public void setDamage_level(String damage_level) {
        this.damage_level = damage_level;
    }

    public String getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(String unit_number) {
        this.unit_number = unit_number;
    }

    public String getHas_damage() {
        return has_damage;
    }

    public void setHas_damage(String has_damage) {
        this.has_damage = has_damage;
    }

    public String getDamage_location() {
        return damage_location;
    }

    public void setDamage_location(String damage_location) {
        this.damage_location = damage_location;
    }

    public String getDamage_type() {
        return damage_type;
    }

    public void setDamage_type(String damage_type) {
        this.damage_type = damage_type;
    }

    public String getDamage_description() {
        return damage_description;
    }

    public void setDamage_description(String damage_description) {
        this.damage_description = damage_description;
    }

    @Override
    public String toString() {
        return "UntisTwo{" +
                "unit_number='" + unit_number + '\'' +
                ", has_damage='" + has_damage + '\'' +
                ", damage_location='" + damage_location + '\'' +
                ", damage_type='" + damage_type + '\'' +
                ", damage_description='" + damage_description + '\'' +
                ", damage_level='" + damage_level + '\'' +
                ", visitID='" + visitID + '\'' +
                ", lane='" + lane + '\'' +
                '}';
    }
}
