package com.nixum.authentication.service;

import com.nixum.model.dao.auth.PermissionMapper;
import com.nixum.model.dao.auth.UserMapper;
import com.nixum.model.model.auth.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class SecurityUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 获取数据库中的用户信息，封装成Security的UserDetails，用于与前端的数据做比对
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        final User user = findDetailByUsername(username);
        // 权限
        final List<SimpleGrantedAuthority> authorities =
                user.getPermissionCodeList().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // 角色
        authorities.add(new SimpleGrantedAuthority(user.getRoleName()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    /**
     * 根据用户名查询用户详细信息，包括角色、权限
     */
    public User findDetailByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> userMap = new HashMap<>(1);
        userMap.put("username", username);
        User user = userMapper.findDetailBy(userMap);
        if (user == null) {
            throw new UsernameNotFoundException("not found this username");
        }
        if ("ROLE_ADMIN".equals(user.getRoleName())) {
            // 超级管理员所有权限都有
            user.setPermissionCodeList(permissionMapper.findAllCode());
        }
        return user;
    }
}
