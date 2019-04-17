package com.nixum.webAdmin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nixum.common.service.AbstractIService;
import com.nixum.model.dao.auth.PermissionMapper;
import com.nixum.model.model.auth.Permission;
import com.nixum.webAdmin.service.IPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 233moutian
 * @date 2018/08/13
 * 后台权限业务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends AbstractIService<Permission> implements IPermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<JSONObject> findAllResourcePermission() {
        return this.permissionMapper.findAllResourcePermission();
    }
}
