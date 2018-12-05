package com.qbk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: quboka
 * @Date: 2018/11/28 18:37
 * @Description: Security配置
 */
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter { //Security 配置适配器


    //密码加密解密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //HttpSecurity 配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http.formLogin()//表单登陆方式
        http.httpBasic() // httpBasic登陆方式
                .and()
                .authorizeRequests()//授权请求
                .anyRequest()//任何请求
                .authenticated();//身份认证
    }
}
