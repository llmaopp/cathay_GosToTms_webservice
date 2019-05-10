package com.example.demo.model.truckInfoModel.truckInfoResponeModel;

import com.example.demo.model.commodel.Header;
import com.example.demo.model.truckInfoModel.truckInfoRequestModel.TruckInfoModel;

/**
 * Created by cathay on 2019/4/24.
 */
public class TruckInfoResponse {
    private Header header;

    private TruckInfoModel bodey;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public TruckInfoModel getBodey() {
        return bodey;
    }

    public void setBodey(TruckInfoModel bodey) {
        this.bodey = bodey;
    }
}
