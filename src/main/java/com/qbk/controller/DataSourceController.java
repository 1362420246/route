package com.qbk.controller;

import com.qbk.datasource.DBContextHolder;
import com.qbk.datasource.DynamicDataSource;
import com.qbk.entity.DataSourceEntity;
import com.qbk.service.DataSourceService;
import com.qbk.util.BaseResult;
import com.qbk.util.BaseResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * @Author: quboka
 * @Date: 2018/12/4 15:38
 * @Description: 数据源控制器
 */
@Api(tags = {"数据源控制器"})
@Log4j2
@RestController
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService ;

    @ApiOperation(value="通用查询数据",notes="通用查询数据接口")
    @GetMapping("/data/{source}/{table}")
    public BaseResult getCommonData(
            @ApiParam(name = "source",value = "数据源id" ,required = true)@PathVariable String source ,
            @ApiParam(name = "table",value = "查询的表名" ,required = true) @PathVariable String table){
        //切换数据库  需要写在service层以前，这样就可以在 open connection 前切换数据库
        DBContextHolder.setDataSource(source);
        return dataSourceService.getCommonData(table );
    }

    @ApiOperation(value="通用执行sql",notes="通用执行sql接口")
    @PostMapping("/data")
    public BaseResult executeSql(
            @ApiParam(name = "sourceId",value = "数据源id" ,required = true) @RequestParam(value = "sourceId")String sourceId ,
            @ApiParam(name = "sql",value = "执行语句" ,required = true) @RequestParam(value = "sql")String sql){
        DBContextHolder.setDataSource(sourceId);
        return dataSourceService.executeSql(sql );
    }

    /**
     * 初始化方法
     */
    @PostConstruct
    private void init(){
        //加载数据源
        dataSourceService.dataSourceLoad();
    }

    @ApiOperation(value="查询数据源列表",notes="查询数据源列表接口")
    @GetMapping(value = "/datasource")
    public BaseResult getSourceList(){
        return dataSourceService.getSourceList();
    }

    @ApiOperation(value="数据源添加",notes="数据源添加接口")
    @PostMapping(value = "/datasource")
    public BaseResult addDataSource(@RequestBody DataSourceEntity dataSourceEntity){
        return dataSourceService.addDataSource(dataSourceEntity);
    }


}
