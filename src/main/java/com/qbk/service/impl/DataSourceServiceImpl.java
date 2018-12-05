package com.qbk.service.impl;

import com.qbk.annotation.SysLog;
import com.qbk.datasource.DBContextHolder;
import com.qbk.datasource.DynamicDataSource;
import com.qbk.entity.DataSourceEntity;
import com.qbk.exception.ServiceException;
import com.qbk.mapper.DataSourceMapper;
import com.qbk.service.DataSourceService;
import com.qbk.util.BaseResult;
import com.qbk.util.BaseResultGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: quboka
 * @Date: 2018/12/4 15:49
 * @Description:
 */
@Service
@Log4j2
@Transactional(rollbackFor = Exception.class ,isolation = Isolation.DEFAULT ,propagation = Propagation.REQUIRED)
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper ;

    @Autowired
    private DynamicDataSource dynamicDataSource ;

    /**
     * 加载数据源
     */
    @Override
    public void dataSourceLoad() {
        List<DataSourceEntity> list = dataSourceMapper.getSourceList();
        for (DataSourceEntity dataSource : list) {
            boolean result = dynamicDataSource.createDataSource(dataSource.getId(),
                    dataSource.getDriveClass(),dataSource.getUrl(),
                    dataSource.getUsername() ,dataSource.getPassword());
            if (result){
                log.info("数据源【{}】加载成功",dataSource.getId());
            }else {
                log.error("数据源【{}】加载失败",dataSource.getId());
            }
        }
    }

    /**
     * 通用查询数据
     */
    @SysLog("通用查询数据")
    @Override
    public BaseResult getCommonData( String table) {
        List<Map<String,Object>> list = dataSourceMapper.getCommonData(table);
        return BaseResultGenerator.success("查询成功",list);
    }

    /**
     * 通用执行sql
     */
    @SysLog("通用执行sql")
    @Override
    public BaseResult executeSql(String sql) {
        int result = dataSourceMapper.executeSql(sql);
        if(result > 0){
            return BaseResultGenerator.success("执行sql成功");
        }else {
            return BaseResultGenerator.error("执行sql失败");
        }
    }

    /**
     *  添加数据源
     */
    @SysLog("添加数据源")
    @Override
    public BaseResult addDataSource(DataSourceEntity dataSourceEntity) {
        //校验数据源
        boolean checkout = dynamicDataSource.testDatasource(
                dataSourceEntity.getDriveClass(), dataSourceEntity.getUrl(),
                dataSourceEntity.getUsername(), dataSourceEntity.getPassword());
        if(!checkout){
            //手动抛出异常
            throw new ServiceException("链接数据源失败");
        }
        DataSourceEntity source = dataSourceMapper.selectSourceById(dataSourceEntity.getId());
        if(source != null){
            return BaseResultGenerator.error("数据源id已存在");
        }
        log.info("添加的数据源："+dataSourceEntity);
        int result = dataSourceMapper.insertSource(dataSourceEntity);
        if(result > 0){
            boolean dataSource = dynamicDataSource.createDataSource(dataSourceEntity.getId(),
                    dataSourceEntity.getDriveClass(),dataSourceEntity.getUrl(),
                    dataSourceEntity.getUsername() ,dataSourceEntity.getPassword());
            if(!dataSource){
                //手动抛出异常
                throw new ServiceException("添加至数据源路由失败");
            }
            return BaseResultGenerator.success();
        }else {
            return BaseResultGenerator.error("插入数据源失败");
        }
    }

    /**
     * 查询数据源列表
     */
    @SysLog("查询数据源列表")
    @Override
    public BaseResult getSourceList() {
        Map<Object, Object> dynamicTargetDataSources = dynamicDataSource.getDynamicTargetDataSources();
        log.info("路由查询数据源列表："+dynamicTargetDataSources);
        //TODO 分页 和 对比
        List<DataSourceEntity> list = dataSourceMapper.getSourceList();
        log.info("数据库查询数据源列表"+list);
        //要返回实际内存中的数据源
        Set<Object> keys = dynamicTargetDataSources.keySet();
        return BaseResultGenerator.success("查询成功",keys);
    }


}
