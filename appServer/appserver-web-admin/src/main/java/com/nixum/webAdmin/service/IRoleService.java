package com.nixum.webAdmin.service;

import com.nixum.common.service.IService;
import com.nixum.model.model.auth.Role;

import java.util.List;

/**
 * @author 233moutian
 * @date 2018/08/13
 * 角色业务接口
 */
public interface IRoleService extends IService<Role> {
    /**
     * 获取所有角色以及对应的权限
     *
     * @return 角色可控资源列表
     */
    List<Role> findAllRoleWithPermission();
    /*
    * 代替上面那个方法啊,用于修复上面那个方法出现的bug
    * */
    List<Role> getAllRoleWithPermission();
}
