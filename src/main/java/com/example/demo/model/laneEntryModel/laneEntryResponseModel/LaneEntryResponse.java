package com.example.demo.model.laneEntryModel.laneEntryResponseModel;

import com.example.demo.model.commodel.Header;
import com.example.demo.model.laneEntryModel.laneEntryRequestModel.LaneEntryModel;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by cathay on 2019/4/24.
 */
@XmlRootElement
@EntityScan
public class LaneEntryResponse implements Serializable {
    private static final long serialVersionUID = 3709812457365436459L;
    private Header header;
    private LaneEntryModel laneEntryModel;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public LaneEntryModel getLaneEntryModel() {
        return laneEntryModel;
    }

    public void setLaneEntryModel(LaneEntryModel laneEntryModel) {
        this.laneEntryModel = laneEntryModel;
    }

    @Override
    public String toString() {
        return "LaneEntryResponse{" +
                "header=" + header +
                ", laneEntryModel=" + laneEntryModel +
                '}';
    }
}
