package com.qbk.exception;


/**
 * 业务层需要自己声明异常的情况
 */
public class ServiceException extends RuntimeException{

    public ServiceException(String msg) {
        super(msg);
    }

}
