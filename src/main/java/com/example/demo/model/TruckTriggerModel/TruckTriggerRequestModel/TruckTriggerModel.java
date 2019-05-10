package com.example.demo.model.TruckTriggerModel.TruckTriggerRequestModel;

import com.example.demo.model.commodel.Header;

/**
 * Created by cathay on 2019/4/23.
 */
public class TruckTriggerModel {
    private TruckTrigger body;
    private Header header;

    public TruckTrigger getBody() {
        return body;
    }

    public void setBody(TruckTrigger body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
