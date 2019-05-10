/**
 * Project Name:    189cn-web
 * File Name:       BaseUni.java
 * Package Name:    com.chinatelecom.web.core.common.uni
 * Date:            2015/6/10 13:07
 * Copyright (c) 2015, chinatelecom-ec All Rights Reserved.
 */
package com.example.demo.utils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;

/**
 * ClassName:   BaseUni <br/>
 * Description:    
 * <ui>
 *   <li>1.接口抽象基类</li>
 * </ui>
 * <br/>
 * Date:        2015/6/10 13:07 <br/>
 *  
 * Modification History:<br/>
 * Date           Author          Version          Discription<br/>
 * -----------------------------------------------------------------------------------<br/>
 * 2015/6/10     XuDongDong         1.0              <初始创建><br/>
 *
 * @author <a href="mailto:xudd@chinatelecom-ec.com.cn">XuDongDong</a>
 * @version 1.0
 * @since 1.1 
 */
public abstract class BaseUni {
    private static final Logger logger = LoggerFactory.getLogger(BaseUni.class);

    /**
     * 执行post请求<br/>
     * 适用-无返回类型-类入参
     *
     * @param url    接口地址
     * @param
     * @return uniResult
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @since 1.1
     */
    protected UniResult excutePost(String url, String str, ContentType contentType) throws Throwable {
        try {
            String ret = HttpClientUtils.post(url, str,contentType);
            UniResult result =new UniResult();
            result.setCode(Status.OK);
            result.setDataObject(ret);
            // 反序列化接口出参
            return result;

        } catch (ConnectTimeoutException ioe) {
            //调用接口超时
            logger.error("调用接口超时, url:{} , params: {}", url, str, contentType);
            return new UniResult(Status.CODE_TIMEDOUT, "调用接口超时");
        } catch (HttpHostConnectException he) {
            // http response 流解析异常
            logger.error("调用接口超时, url:{} ",  url, str, contentType);
            return new UniResult<>(Status.CODE_TIMEDOUT, he.getMessage());
        } catch (ClientProtocolException ioe) {
            //返回非200的http状态码
            logger.error("返回非200的http状态码, url:{} , params: {}",  url, str, contentType);
            return new UniResult(Status.CODE_HTTP_STATUSCODE, ioe.getMessage());
        } catch (ParseException pe) {
            //http response 流解析异常
            logger.error("流解析异常, url:{} , params: {}", url, str, contentType);
            return new UniResult(Status.CODE_PARSEEXCEPTION, pe.getMessage());
        }
    }
}   