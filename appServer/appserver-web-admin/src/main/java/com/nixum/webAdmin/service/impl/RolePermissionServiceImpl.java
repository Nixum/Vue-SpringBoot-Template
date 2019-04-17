package com.nixum.webAdmin.service.impl;

import com.nixum.common.service.AbstractIService;
import com.nixum.model.dao.auth.RolePermissionMapper;
import com.nixum.model.model.auth.RolePermission;
import com.nixum.webAdmin.service.IRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 233moutian
 * @date 2018/08/13
 * 角色权限业务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends AbstractIService<RolePermission> implements IRolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

}
