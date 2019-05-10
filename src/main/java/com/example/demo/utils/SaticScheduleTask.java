package com.example.demo.utils;

import com.example.demo.model.EntityCache;
import com.example.demo.protoModel.Gos;
import com.example.demo.service.googleProtoToJavaBean.GoogleProtoToJavaBeanService;
import com.example.demo.service.iCacheManager.CacheManager;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Component //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class SaticScheduleTask{
    private final static Logger logger = LoggerFactory.getLogger(SaticScheduleTask.class);
    @Autowired
    // 损伤接口
    private GoogleProtoToJavaBeanService googleProtoToJavaBeanService;

    @Autowired
    private CacheManager cacheManager;

    //3.添加定时任务
    @Scheduled(fixedRate = 100)
    @PostConstruct
    public void startTime() {

        if(cacheManager.getCacheAll().size()!=0){
            Map<String, EntityCache> map= cacheManager.getCacheAll();

            //遍历map中的键
            int t=0;
            for (String key : map.keySet()) {
                EntityCache entityCache=   map.get(key);
                Gos.GOSAny gosAny= (Gos.GOSAny) entityCache.getDatas();
                logger.info("接收数据： GateInOutProto"+gosAny);
                    System.out.println(key);
                try {
                   Boolean j= googleProtoToJavaBeanService.toJavaBean(gosAny);
                    if(j){
                        cacheManager.clearByKey(key);
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }

                logger.info("删除缓存"+key);
            }
        }
    }

}
