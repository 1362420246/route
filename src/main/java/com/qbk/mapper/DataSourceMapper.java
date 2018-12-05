package com.qbk.mapper;

import com.qbk.entity.DataSourceEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 添加了@Mapper注解之后这个接口在编译时会生成相应的实现类
 *
 * 需要注意的是：这个接口中不可以定义同名的方法，因为会生成相同的id
 * 也就是说这个接口是不支持重载的
 */
@Mapper
public interface DataSourceMapper {

    /**
     * 通用查询数据  ${table}
     */
    @Select("SELECT * FROM ${table}")
    List<Map<String,Object>> getCommonData(@Param(value = "table") String table);

    /**
     * 通用执行sql  ${sql} 增删改
     */
    @Update("${sql}")
    int executeSql(@Param(value = "sql") String sql);

    /**
     *  根据id查询数据源
     */
    @Select("SELECT id , drive_class AS driveClass ,url , username ,password FROM t_data_source WHERE id = #{id}")
    DataSourceEntity selectSourceById(String id);

    /**
     * 插入数据源
     */
    @Insert("INSERT INTO t_data_source ( id , drive_class ,url , username ,password)VALUES (#{id},#{driveClass},#{url},#{username},#{password})")
    int insertSource(DataSourceEntity dataSourceEntity);

    /**
     * 查询数据源列表
     */
    @Select("SELECT id , drive_class AS driveClass ,url , username ,password FROM t_data_source")
    List<DataSourceEntity> getSourceList();

    /**
     * 删除数据源
     */
    @Delete("DELETE FROM t_data_source WHERE id = #{sourceId}")
    int deleteSource(@Param("sourceId") String sourceId);

}
