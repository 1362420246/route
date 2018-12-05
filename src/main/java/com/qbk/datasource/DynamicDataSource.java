package com.qbk.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Set;

/**
 * @Author: quboka
 * @Date: 2018/12/3 15:04
 * @Description: 数据源路由
 */
@Log4j2
//AbstractRoutingDataSource 实现了DataSource接口 和 InitializingBean 接口  初始化方法
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     *  数据源集合
     */
    private Map<Object, Object> dynamicTargetDataSources;
    /**
     * 默认数据源
     */
    private Object dynamicDefaultTargetDataSource;

    public Map<Object, Object> getDynamicTargetDataSources() {
        return dynamicTargetDataSources;
    }

    public void setDynamicTargetDataSources(Map<Object, Object> dynamicTargetDataSources) {
        this.dynamicTargetDataSources = dynamicTargetDataSources;
    }

    public Object getDynamicDefaultTargetDataSource() {
        return dynamicDefaultTargetDataSource;
    }

    public void setDynamicDefaultTargetDataSource(Object dynamicDefaultTargetDataSource) {
        this.dynamicDefaultTargetDataSource = dynamicDefaultTargetDataSource;
    }

    /**
     * 在open connection**时触发
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DBContextHolder.getDataSource();
        if (StringUtils.isEmpty(datasource)) {
            log.info("---当前数据源：默认数据源---");
        } else {
            log.info("---当前数据源：" + datasource + "---");
        }
        return datasource;
    }

    /**
     * 设置默认数据源
     */
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        this.dynamicDefaultTargetDataSource = defaultTargetDataSource;
    }

    /**
     * 设置数据源集合
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.dynamicTargetDataSources = targetDataSources;
    }

    /**
     * 创建数据源
     * @param key
     * @param driveClass
     * @param url
     * @param username
     * @param password
     * @return
     */
    public boolean createDataSource(String key, String driveClass, String url, String username, String password) {
        try {
            try {
                // 排除连接不上的错误
                Class.forName(driveClass);
                // 相当于连接数据库
                DriverManager.getConnection(url, username, password);
            } catch (Exception e) {
                return false;
            }
            @SuppressWarnings("resource")
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(key);
            druidDataSource.setDriverClassName(driveClass);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
            druidDataSource.setMaxWait(60000);
            druidDataSource.setFilters("stat");
            DataSource createDataSource = (DataSource) druidDataSource;
            druidDataSource.init();
            Map<Object, Object> map = this.dynamicTargetDataSources;
            // 加入map
            map.put(key, createDataSource);
            // 将map赋值给父类的TargetDataSources
            setTargetDataSources(map);
            // 将TargetDataSources中的连接信息放入resolvedDataSources管理
            super.afterPropertiesSet();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除数据源
     */
    public boolean delDatasources(String key) {
        Map<Object, Object> targetDataSource = this.dynamicTargetDataSources;
        if (targetDataSource.containsKey(key)) {
            Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
            for (DruidDataSource druidDataSource : druidDataSourceInstances) {
                if (key.equals(druidDataSource.getName())) {
                    System.out.println(druidDataSource);
                    targetDataSource.remove(key);
                    DruidDataSourceStatManager.removeDataSource(druidDataSource);
                    // 将map赋值给父类的TargetDataSources
                    setTargetDataSources(targetDataSource);
                    // 将TargetDataSources中的连接信息放入resolvedDataSources管理
                    super.afterPropertiesSet();
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 测试数据源连接是否有效
     */
    public boolean testDatasource(String driveClass, String url, String username, String password) {
        try {
            Class.forName(driveClass);
            DriverManager.getConnection(url, username, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}