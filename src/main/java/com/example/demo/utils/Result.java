package com.example.demo.utils;


import java.io.Serializable;

/**
 * 抽象类：返回结果
 */
public abstract class Result<T> implements Serializable {

    /**
     * 错误码
     */
    private Object code;

    /**
     * 错误对象
     */
    private Object errorDescription;

    /**
     * 返回数据
     */
    private T dataObject;

    /**
     * 返回状态码
     */
    private T falg;

    public Result(Status status, Object errorDescription) {
        this.code = status;
        this.errorDescription = errorDescription;
    }

    public Result() {
    }

    /**
     * 返回code值
     *
     * @return code
     */
    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    /**
     * 返回错误描述
     *
     * @return errorDescription ，返回结果为成功时，为空
     */
    public Object getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(Object errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * 调用成功返回的数据模型
     *
     * @return dataObject 返回错误时，该值可以为空
     */
    public T getDataObject() {
        return dataObject;
    }

    public void setDataObject(T dataObject) {
        this.dataObject = dataObject;
    }

    public T getFalg() {
        return falg;
    }

    public void setFalg(T falg) {
        this.falg = falg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", errorDescription=" + errorDescription +
                ", dataObject=" + dataObject +
                ", falg=" + falg +
                '}';
    }
}
