package com.example.demo.model.commodel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Created by cathay on 2019/4/18.
 */
@EntityScan
public class Header {
    //128位UUID，格式十六进制。每个事务都是唯一的(相应请求和答复中的值相同)。
    private String messageID;
    private String messageType;
    //消息时间戳，ISO 8601格式，当地时间，例如2003-02-20T15：53：51
    private String timestamp;
    //标识事务的类型，确定如何解释<body/>，例如快照标识。消息的根元素为请求消息携带事务元素的值，事务元素的值“Response”用于应答消息。
    private String transaction;
    //独特的标识卡车访问在苏丹。如果在GOS访问上下文中使用，必须使用
    private String visitID;
    //唯一识别卡车位置。
    private String lane;
    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

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

    @Override
    public String toString() {
        return "Header{" +
                "messageID='" + messageID + '\'' +
                ", messageType='" + messageType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", transaction='" + transaction + '\'' +
                ", visitID='" + visitID + '\'' +
                ", lane='" + lane + '\'' +
                ", error=" + error +
                '}';
    }
}
