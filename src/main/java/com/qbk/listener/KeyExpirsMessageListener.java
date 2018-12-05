package com.qbk.listener;

import com.qbk.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 *  redis 监听
 */
@Log4j2
@Component
public class KeyExpirsMessageListener implements MessageListener {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        log.info("redis的key:{}到期",key);
        if(key.contains("key:")) {

        }
    }
}
