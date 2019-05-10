package com.example.demo.model;

import com.example.demo.model.commodel.Identifiable;

/**
 * 接口监控
 * 高超  2019年1月4日10:23:15
 */
public class InterfaceMonitor implements Identifiable {

	private static final long serialVersionUID = -7399617937272895076L;

    /**
     *id唯一标识
     */
    private String id;
	/**
	 *对应接口url
	 */
	private String interfaceUrl;
    /**
     *请求json
     */
    private String reqJson;

    /**
     *响应json
     */
    private String resJson;

    /**
     *请求方式
     */
    private String reqType;
    /**
     *响应code
     */
    private String resCode;
    /**
     *接口响应时间
     */
    private String interfaceRunTime;
    /**
     *创建时间
     */
    private String createTime;

    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterfaceUrl() {return interfaceUrl;}
	public void setInterfaceUrl(String interfaceUrl) {this.interfaceUrl = interfaceUrl;}
	public String getReqJson() {
		return reqJson;
	}
	public void setReqJson(String reqJson) {
		this.reqJson = reqJson;
	}
	public String getResJson() {
		return resJson;
	}
	public void setResJson(String resJson) {
		this.resJson = resJson;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getInterfaceRunTime() {
		return interfaceRunTime;
	}
	public void setInterfaceRunTime(String interfaceRunTime) {
		this.interfaceRunTime = interfaceRunTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "InterfaceMonitor{" +
				"id='" + id + '\'' +
				", interfaceUrl='" + interfaceUrl + '\'' +
				", reqJson='" + reqJson + '\'' +
				", resJson='" + resJson + '\'' +
				", reqType='" + reqType + '\'' +
				", resCode='" + resCode + '\'' +
				", interfaceRunTime='" + interfaceRunTime + '\'' +
				", createTime='" + createTime + '\'' +
				'}';
	}
}
