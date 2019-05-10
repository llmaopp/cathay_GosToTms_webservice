package com.example.demo.model.truckInfoModel.truckInfoRequestModel;



import java.util.List;

/**
 * Created by cathay on 2019/4/24.
 */
public class TruckInfoModel {
  private List<String> rfid_license_plate_tag;

    public List<String> getRfid_license_plate_tag() {
        return rfid_license_plate_tag;
    }

    public void setRfid_license_plate_tag(List<String> rfid_license_plate_tag) {
        this.rfid_license_plate_tag = rfid_license_plate_tag;
    }
}
