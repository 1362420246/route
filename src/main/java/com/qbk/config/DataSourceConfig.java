package com.qbk.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.qbk.constant.DataSourceConstant;
import com.qbk.datasource.DynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: quboka
 * @Date: 2018/12/3 16:26
 * @Description:  数据源配置
 */
@Configuration
//mapper扫描
@MapperScan(basePackages="com.qbk.mapper")
public class DataSourceConfig {

    /**
     * 配置数据源
     */
    @Bean("dataSource")
    @Qualifier("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")//读取配置文件
    public DataSource dataSource(){
        //druid 数据源
        return DruidDataSourceBuilder.create().build();
    }

    /**
     *  配置数数据路由（也是数据源）
     */
    @Primary //表示默认
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource( @Qualifier("dataSource")DataSource dataSource){
        //创建数据源集合
        Map<Object,Object> targetDataSource = new HashMap<>(1);
        targetDataSource.put(DataSourceConstant.DEFAULT_DATA_SOURCE_ID,dataSource);
        //创建数据路由对象
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置数据源集合
        dynamicDataSource.setTargetDataSources(targetDataSource);
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource);
        return dynamicDataSource ;
    }

}
