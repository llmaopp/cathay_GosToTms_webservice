package com.example.demo.service;

import com.example.demo.protoModel.Gos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zeromq.ZMQ;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by cathay on 2019/4/19.
 */
@Service
public class ZmqClient extends Thread{

    private final static Logger logger = LoggerFactory.getLogger(ZmqClient.class);
    // 第三方接口地址
    @Value("${clientZmq.clientZmqIp}")
    private String clientZmqIp;
    @Value("${clientZmq.clientZmqHost}")
    private String clientZmqHost;
    private static List<Gos.GOSAny> sendList = new CopyOnWriteArrayList<Gos.GOSAny>();
    public  void run() {
        try {
            startZMQServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("启动失败"+e);
        }

    }

    private void startZMQServer() throws InterruptedException {
        //准备上下文和套接字
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://"+clientZmqIp+":"+clientZmqHost+"");
        while (!Thread.currentThread().isInterrupted()) {
            Iterator<Gos.GOSAny> iterator = sendList.iterator();
            while(iterator.hasNext()){
                publisher.send(iterator.next().toByteArray(), 0);
                logger.info("消息发送成功");
                if (sendList.size() > 0) {
                    sendList.remove(0);
                } else {
                    logger.warn("sendList.length == 0");
                }
            }
            Thread.sleep(1);

        }
    }

    public void send (Gos.GOSAny gosAny) {
        try {
                logger.info("Send: {}", gosAny);
                // 转换成GBK 进行发送数据
                sendList.add(gosAny);

        } catch (Exception e) {
            logger.error("发送信息异常", e);
        }
    }
}
