package com.example.demo.model.contextDataModel.ContextDataResponseModel;

import com.example.demo.model.commodel.Header;
import com.example.demo.model.contextDataModel.ContextDataRequestModel.ContextDataModel;

import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
public class ContextDataResponse  implements Serializable {
    private static final long serialVersionUID = -5402455961010216699L;
    private Header header;

    private ContextDataModel contextDataModel;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ContextDataModel getContextDataModel() {
        return contextDataModel;
    }

    public void setContextDataModel(ContextDataModel contextDataModel) {
        this.contextDataModel = contextDataModel;
    }

    @Override
    public String toString() {
        return "ContextDataResponse{" +
                "header=" + header +
                ", contextDataModel=" + contextDataModel +
                '}';
    }
}
