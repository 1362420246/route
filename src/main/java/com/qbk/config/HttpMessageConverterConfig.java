package com.qbk.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * HttpMessageConverter  消息转换器配置
 */
@Configuration
public class HttpMessageConverterConfig {

    /**
     *  引入Fastjson解析json，不使用默认的jackson
     *  必须在pom.xml引入fastjson的jar包，并且版必须大于1.2.10
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastConf = new FastJsonConfig();
        fastConf.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // WriteMapNullValue 是否输出为null的字段,若为null 则显示该字段
        //  WriteNullStringAsEmpty  字符类型字段如果为null,输出为"",而非null
        fastConf.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty);
        fastJsonConverter.setFastJsonConfig(fastConf);
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        supportedMediaTypes.clear();
        supportedMediaTypes.add(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"));
        stringConverter.setSupportedMediaTypes(supportedMediaTypes);
        return new HttpMessageConverters(fastJsonConverter);
    }
}
