package com.example.demo.utils;


import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 接口返回结果集
 * Created by XuDongDong on 2015/3/3.
 */
@EntityScan
public class UniResult<T> extends Result<T> {

    public UniResult(Status status, String errorDescription) {
        super(status, errorDescription);
    }

    public UniResult() {
        super();
    }
}
