package com.qbk.util;


/**
 * BaseResult生成器
 *
 * @author dazzlzy
 * @date 2018/4/1
 */
public class BaseResultGenerator {

    /**
     * 生成返回结果
     *
     * @param code    返回编码
     * @param message 返回消息
     * @param data    返回数据
     * @param <T>     返回数据类型
     * @return 返回结果
     */
    public static <T> BaseResult<T> generate(boolean code, String message, T data) {
        return new BaseResult<>(code, message, data);
    }

    /**
     * 操作成功响应结果， 默认结果
     *
     * @return 操作成功的默认响应结果
     */
    public static <T> BaseResult<T> success(String msg) {
        return new BaseResult<>(true, msg, null);
    }
    /**
     * 操作成功响应结果， 默认结果
     *
     * @return 操作成功的默认响应结果
     */
    public static <T> BaseResult<T> success() {
        return new BaseResult<>(true, "成功", null);
    }

    /**
     * 操作成功响应结果， 自定义数据及信息
     *
     * @param message 自定义信息
     * @param data    自定义数据
     * @param <T>     自定义数据类型
     * @return 响应结果
     */
    public static <T> BaseResult<T> success(String message, T data) {
        return new BaseResult<>(true, message, data);
    }



    /**
     * 异常响应结果， 自定义错误编码及信息
     *
     * @param message 自定义信息
     * @return 响应结果
     */
    public static <T> BaseResult<T> error(String message) {
        return new BaseResult<>(false, message, null);
    }
/**
     * 异常响应结果， 自定义错误编码及信息
     *

     * @return 响应结果
     */
    public static <T> BaseResult<T> error() {
        return new BaseResult<>(false, "失败", null);
    }


}
