package com.example.demo.model.commodel;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cathay on 2019/4/23.
 */
@EntityScan
public class MQMessage {
    private Integer productCode; // 生产者代码
    private Integer consumerCode; // 消费者代码
    private String messageId; // 消息唯一标识
    private Integer event; // 消息监听事件
    private Integer action; //操作：1加，2减
    private Date created; // 消息发送时间
    private Map<String, Object> bussinessBody;

    private MQMessage() {
        super();
    }

    private MQMessage(Integer productCode, Integer consumerCode, String messageId, Integer event, Date created,
                      Map<String, Object> bussinessBody, Integer action) {
        super();
        this.productCode = productCode;
        this.consumerCode = consumerCode;
        this.messageId = messageId;
        this.event = event;
        this.created = created;
        this.bussinessBody = bussinessBody;
        this.action = action;
    }

    private MQMessage(Integer productCode, Integer consumerCode, Integer event, Map<String, Object> bussinessBody, Integer action) {
        super();
        this.productCode = productCode;
        this.consumerCode = consumerCode;
        this.event = event;
        this.bussinessBody = bussinessBody;
        this.action = action;
    }

    public static String productMQMessage(Integer productCode, Integer consumerCode, Integer event, Map<String, Object> bussinessBody, Integer action) {
        MQMessage mqObj = new MQMessage(productCode, consumerCode, event, bussinessBody, action);
        mqObj.setCreated(new Date());
        mqObj.setMessageId(generatSeriaeNo());

        return JSON.toJSONString(mqObj);
    }

    //生成消息唯一标识
    private static String generatSeriaeNo() {
        String uuid2 = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid2;

    }

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public Integer getConsumerCode() {
        return consumerCode;
    }

    public void setConsumerCode(Integer consumerCode) {
        this.consumerCode = consumerCode;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Map<String, Object> getBussinessBody() {
        return bussinessBody;
    }

    public void setBussinessBody(Map<String, Object> bussinessBody) {
        this.bussinessBody = bussinessBody;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "MQMessage [productCode=" + productCode + ", consumerCode="
                + consumerCode + ", messageId=" + messageId + ", event="
                + event + ", action=" + action + ", created=" + created
                + ", bussinessBody=" + bussinessBody + "]";
    }

}
