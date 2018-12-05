package com.qbk.service;

import com.qbk.entity.DataSourceEntity;
import com.qbk.util.BaseResult;

import java.util.List;

/**
 * @Author: quboka
 * @Date: 2018/12/4 15:49
 * @Description:
 */
public interface DataSourceService {

    /**
     * 加载数据源
     */
    void dataSourceLoad();

    /**
     * 通用查询数据
     */
    BaseResult getCommonData(String table);

    /**
     * 通用执行sql
     */
    BaseResult executeSql(String sql);

    /**
     *  添加数据源
     */
    BaseResult addDataSource(DataSourceEntity dataSourceEntity);

    /**
     * 查询数据源列表
     */
    BaseResult getSourceList();

    /**
     * 删除数据源
     */
    BaseResult deleteSource(String sourceId);
}
