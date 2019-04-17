package com.nixum.webAdmin.service;


import com.alibaba.fastjson.JSONObject;
import com.nixum.common.service.IService;
import com.nixum.model.model.auth.Permission;

import java.util.List;

/**
 * @author 233moutian
 * @date 2018/08/13
 * 权限资源业务接口
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 找到所有权限可控资源
     *
     * @return Json对象列表
     */
    List<JSONObject> findAllResourcePermission();
}
