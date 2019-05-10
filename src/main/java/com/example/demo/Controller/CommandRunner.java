package com.example.demo.Controller;

import com.example.demo.service.ZmqClient;
import com.example.demo.service.ZmqServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandRunner implements CommandLineRunner {

  // 日志模块
  private final static Logger logger = LoggerFactory.getLogger(CommandRunner.class);

  public Boolean hasInit = false;

  @Autowired
  private ZmqClient zmqClient;
  @Autowired
  private ZmqServer zmqServer;

  /**
   * 数据采集端  设备作为客户端，数据采集端作为服务端
   */
  @Override
  public void run(String... args) {
    try {
      logger.info("初始化...");
      zmqServer.start();
      zmqClient.start();

      logger.info("初始化成功");
    } catch (Exception e) {
      logger.error("初始化创建ZMQ异常", e);
    }
  }

}