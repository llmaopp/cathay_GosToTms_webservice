package com.example.demo.model.commodel;

import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 主键标识
 *
 * @author ZhangShengfei
 * @date 2016年9月1日上午10:55:34
 */
@Service
public interface Identifiable extends Serializable {
    /**
     * 获取主键
     */
    String getId();

    /**
     * 设置ID属性
     *
     * @param id 主键
     */
    void setId(String id);
}
