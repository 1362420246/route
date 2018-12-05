package com.qbk.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: quboka
 * @Date: 2018/11/28 13:33
 * @Description: 请求时间过滤器
 */
@Log4j2
@Component
@WebFilter(urlPatterns = "/*", filterName = "timeFilter")
//@Order中的value越小，优先级越高
@Order(value = 1)
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("time filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("time filter,请求:【{}】耗时:" + (System.currentTimeMillis() -start ),((HttpServletRequest)servletRequest).getRequestURI() );
    }

    @Override
    public void destroy() {
        log.info("time filter destroy");
    }
}
