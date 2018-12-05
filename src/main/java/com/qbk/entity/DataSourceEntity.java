package com.qbk.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: quboka
 * @Date: 2018/12/4 15:05
 * @Description: 数据源实体
 */
@ApiModel(value = "DataSourceEntity",description="数据源实体")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceEntity {

    @ApiModelProperty(value = "数据源id")
    private String id ;
    @ApiModelProperty(value = "数据源驱动")
    private String driveClass ;
    @ApiModelProperty(value = "数据源url")
    private String url ;
    @ApiModelProperty(value = "数据源账号")
    private String username ;
    @ApiModelProperty(value = "数据源密码")
    private String password ;


}
