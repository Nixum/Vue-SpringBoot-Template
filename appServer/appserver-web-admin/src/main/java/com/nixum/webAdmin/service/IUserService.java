package com.nixum.webAdmin.service;

import com.nixum.common.service.IService;
import com.nixum.model.model.auth.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author 233moutian
 * @date 2018/08/13
 * 有用户业务接口
 */
public interface IUserService extends IService<User> {
    /**
     * 获取所有用户以及对应角色
     *
     * @return 用户列表
     */
    List<User> findAllUserWithRole();

    /**
     * 按条件查询用户信息
     *
     * @param column 列名
     * @param param  参数map
     * @return 用户
     */
    User findDetailBy(String column, Object param);

    /**
     * 按用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户
     * @throws UsernameNotFoundException 用户名找不到
     */
    User findDetailByUsername(String username) throws UsernameNotFoundException;

    /**
     * 按用户名更新最后一次登录时间
     *
     * @param username 用户名
     */
    void updateLoginTimeByUsername(String username);

    /**
     * 验证用户密码
     *
     * @param rawPassword     原密码
     * @param encodedPassword 加密后的密码
     * @return boolean
     */
    boolean verifyPassword(String rawPassword, String encodedPassword);
}
