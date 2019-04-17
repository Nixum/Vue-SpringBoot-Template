package com.nixum.authentication.config;

import com.nixum.authentication.jwt.JwtAuthenticationEntryPoint;
import com.nixum.authentication.jwt.JwtAuthenticationFilter;
import com.nixum.authentication.service.SecurityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * 安全设置配置类
 * 可以在这里设置不拦截的接口方法
 *
 * @author Zoctan
 * @date 2018/06/09
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    @Override
    public SecurityUserDetailsService userDetailsService() {
        return new SecurityUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证用户信息和权限
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        // 自定义获取用户信息
        auth.userDetailsService(this.userDetailsService())
                // 设置密码加密
                .passwordEncoder(this.passwordEncoder());
    }

    /**
     * 配置url资源请求拦截
     */
    @Override
    protected void configure(final HttpSecurity http)
            throws Exception {

        // 关闭cors
        http.cors().disable()
                // 关闭csrf
                .csrf().disable()
                // 无状态Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 异常处理
                .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and()
                // 对所有的请求都做权限校验
                .authorizeRequests()
                // 允许登录和注册, 对于获取token的rest api要允许匿名访问
                .antMatchers(
                        HttpMethod.POST,
                        "/user/login",
                        "/user/logout"
                ).permitAll()
                // 允许对外的api接口匿名访问
                .antMatchers("/api/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated().and();
        // 基于定制JWT安全过滤器，这里其实UsernamePasswordAuthenticationFilter并没有被用到
        // 登录验证全程交由控制器/user/login处理
        // UsernamePasswordAuthenticationFilter的请求路径是/login需要配合formLogin().loginProcessingUrl("/login")
        // 所以全程并没有被使用到，同理，上面的加密也没什么用了
        // 一般来说最好是重写UsernamePasswordAuthenticationFilter，把控制器的验证内容搬到这个过滤器，ps：该filter只解析表单数据
        // UsernamePasswordAuthenticationFilter负责包装用户名密码，DaoAuthenticationProvider负责验证密码是否正确
        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 禁用页面缓存
        http.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不拦截静态资源，需要与mvc的addResourceHandlers方法配合
        web.ignoring().antMatchers(
                "/",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
        );
    }
}