package com.nixum.webAdmin.service;

import com.nixum.common.service.IService;
import com.nixum.model.model.auth.User;
import com.nixum.model.model.auth.UserRole;

/**
 * @author 233moutian
 * @date 2018/08/13
 * 用户角色业务接口
 */
public interface IUserRoleService extends IService<UserRole> {
    /**
     * 更新用户角色
     *
     * @param user 用户
     */
    void updateUserRole(User user);
}
