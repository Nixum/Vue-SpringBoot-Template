package com.nixum.authentication.jwt;

import com.nixum.common.constant.RedisConstant;
import com.nixum.common.redis.JedisUtil;
import com.nixum.common.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份认证过滤器 || 使用cors解决跨域问题
 *
 * @author Zoctan
 * @date 2018/06/09
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private JedisUtil jedisUtil;

    @Override
    protected void doFilterInternal(@Nonnull final HttpServletRequest request, @Nonnull final HttpServletResponse response, @Nonnull final FilterChain filterChain)
            throws ServletException, IOException {
        // 简单防御csrf，检查请求头中的origin字段
        String originUrl = request.getHeader("Origin");
        // startWith内添加可放行的域名，此时对外开放的Api也需要加这个请求头
//        if (StringUtils.isBlank(originUrl) || (!originUrl.trim().startsWith(""))) {
//            return ;
//        }

        // 解决跨域问题,*需替换成可允许请求来源的url
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With");
        // 明确允许通过的方法,以及允许获取的响应头
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Expose-Headers", "*");

        // 预请求后，直接返回
        // 返回码必须为 200 否则视为请求失败
        if ("OPTIONS".equals(request.getMethod())) {
            return;
        }

        final String token = this.jwtUtil.getTokenFromRequest(request);
        if (token == null) {
            log.info("JwtFilter => Anonymous<> IP<{}> requestURL<{}> Method<{}>",
                    IpUtil.getIpAddress(request), request.getRequestURL(), request.getMethod());
        } else {
            final String username = this.jwtUtil.getUsername(token);
            log.info("JwtFilter => user<{}> token<{}> requestURL<{}> Method<{}>",
                    username, token, request.getRequestURL(), request.getMethod());
            // 判断用户登录是否过时
            String tokenInRedis = jedisUtil.StringProcessor.get(username);
            if (StringUtils.isNotBlank(tokenInRedis) && tokenInRedis.equals(token)) {
                jedisUtil.StringProcessor.setEx(username, token, RedisConstant.EXPIRE_NINETY);
            } else {
                if (!request.getRequestURI().equals("/user/logout")) {
                    request.getRequestDispatcher("/user/obsolete").forward(request, response);
                    return ;
                }
            }

            if (StringUtils.isNotBlank(username)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UsernamePasswordAuthenticationToken authentication = this.jwtUtil.getAuthentication(username, token);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("JwtFilter => user<{}> is authorized, set security context", username);
            }
        }
        filterChain.doFilter(request, response);
    }
}
