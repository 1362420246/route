package com.qbk.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应结果
 * 如果使用注解@JsonInclude(JsonInclude.Include.NON_NULL)： 则会保证序列化json的时候,如果是null的对象,key也会消失
 *
 * @author dazzlzy
 * @date 2018/3/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> implements Serializable {
    @ApiModelProperty(value = "状态码")
    private boolean code;
    @ApiModelProperty(value = "信息")
    private String msg;
    @ApiModelProperty(value = "数据对象")
    private T data;

}
