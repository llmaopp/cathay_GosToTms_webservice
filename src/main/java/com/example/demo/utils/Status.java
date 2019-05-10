package com.example.demo.utils;


import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * ClassName: Status <br/>
 * Description:执行状态 <br/>
 * Date:	2016/9/2 11:06<br/>
 * <p/>
 * Modification History:<br/>
 * Date           Author          Version          Description<br/>
 * -----------------------------------------------------------------------------------<br/>
 * 2016/9/2    张胜飞         1.0              <初始创建><br/>
 *
 * @author <a href="mailto:hotyei2002@163.com">ZhangShengfei</a>
 * @version 1.0
 * @since 1.0
 */
@EntityScan
public enum Status {
    OK(0, "OK"),
    ERROR(1, "ERROR"),
    CODE_TIMEDOUT(1002, "接口调用超时错误码"),
    CODE_HTTP_STATUSCODE(1003, "http状态码错误"),
    CODE_PARSEEXCEPTION(1004, "http response 解析流错误"),
    CODE_URISYNTAXEXCEPTION(1005, "构造接口地址异常"),
    CODE_CONVERSIONDATA08(1008, "转换成xml发生异常"),
    CODE_CONVERSIONDATA09(1009, "googel proto 与javaBean 转换异常"),
    CODE_CONVERSIONDATA10(1010, "转换成avaBean转换xml 调用第三方几口之前发生异常"),
    CODE_CONVERSIONDATA11(1011, "系统发生未知异常");


    private Object code;
    private String desc;

    Status(Object code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Status valueOfEnum(Object code) {
        Status[] iss = values();
        for (Status cs : iss) {

            if (code instanceof String) {
                if (cs.getCode().equals(code)) {
                    return cs;
                }
            } else if (code instanceof Integer) {
                if (cs.getCode() == code) {
                    return cs;
                }
            }
        }
        return null;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
