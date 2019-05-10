package com.example.demo.service;

import com.example.demo.protoModel.Gos;
import com.example.demo.service.iCacheManager.impl.CacheManagerImpl;
import com.example.demo.utils.SaticScheduleTask;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zeromq.ZMQ;

/**
 * Created by cathay on 2019/4/18.
 */

@Component
@Service
public class ZmqServer extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(ZmqServer.class);
    // 第三方接口地址
    @Value("${serverZmq.serverZmqIp}")
    private String serverZmqIp;
    @Value("${serverZmq.serverZmqHost}")
    private String serverZmqHost;
    @Autowired
    private SaticScheduleTask saticScheduleTask;
    public void run() {
        CacheManagerImpl cacheManagerImpl = new CacheManagerImpl();
        //准备上下文
        //套接字连接至服务器
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://"+serverZmqIp+":"+serverZmqHost);
        //订阅任何主题（An empty 'option_value' of length zero shall subscribe to all incoming messages）
        subscriber.subscribe("".getBytes());
        int t=0;
        while (!Thread.currentThread().isInterrupted()) {
            byte[] bytes = subscriber.recv();
            if(bytes.length>0){
                try{
                    Gos.GOSAny gosAny= Gos.GOSAny.parseFrom(bytes);
                       String gosAny1=gosAny.getAny().getTypeUrl()+t++;
                        cacheManagerImpl.putCache(gosAny1,gosAny,0);

                    logger.info("收到消息"+gosAny1);
                } catch (InvalidProtocolBufferException e) {
                    logger.info("错误信息"+e);
                }
            }else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.info("线程sleep休息报错"+e);
                }
            }
        }
    }
}
