package com.qbk.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: quboka
 * @Date: 2018/11/27 10:28
 * @Description: 登陆拦截器
 */
@Log4j2
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     在请求前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器：请求路径：{}", request.getRequestURI());
        return super.preHandle(request,response,handler );
    }

    /**
     // 该方法也是需要当前对应的Interceptor 的preHandle 方法的返回值为true 时才会执行, postHandle
     // 方法，顾名思义就是在当前请求进行处理之后，也就是Controller 方法调用之后执行，但是它会在DispatcherServlet
     // 进行视图返回渲染之前被调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     // 方法afterCompletion:该方法也是需要当前对应的Interceptor 的preHandle 方法的返回值为true
     // 时才会执行。顾名思义，该方法将在整个请求结束之后，也就是在DispatcherServlet
     // 渲染了对应的视图之后执行。这个方法的主要作用是用于进行资源清理工作的。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     //异步请求
     //相对于HandlerInterceptor，HandlerInterceptorAdapter多了一个实现方法afterConcurrentHandlingStarted()，它来自HandlerInterceptorAdapter的直接实现类AsyncHandlerInterceptor,AsyncHandlerInterceptor接口直接继承了HandlerInterceptor，
     //并新添了afterConcurrentHandlingStarted()方法用于处理异步请求，当Controller中有异步请求方法的时候会触发该方法时，异步请求先支持preHandle、然后执行afterConcurrentHandlingStarted。异步线程完成之后执行preHandle、postHandle、afterCompletion。
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
