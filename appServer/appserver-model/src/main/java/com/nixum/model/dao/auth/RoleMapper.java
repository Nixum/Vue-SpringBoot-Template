package com.nixum.model.dao.auth;

import com.nixum.common.dao.NewBaseMapper;
import com.nixum.model.model.auth.Role;
import com.nixum.model.model.base.Resource;

import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
public interface RoleMapper extends NewBaseMapper<Role> {
    /**
     * 获取所有角色以及对应的权限
     *
     * @return 角色可控资源列表
     */
    List<Role> findAllRoleWithPermission();
    List<Role> findAllRole();
    List<Resource> findAllRolePermission(Long rId);
}