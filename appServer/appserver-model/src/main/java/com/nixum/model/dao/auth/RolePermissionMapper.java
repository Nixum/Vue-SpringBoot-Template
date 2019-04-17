package com.nixum.model.dao.auth;

import com.nixum.common.dao.NewBaseMapper;
import com.nixum.model.model.auth.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
public interface RolePermissionMapper extends NewBaseMapper<RolePermission> {
    /**
     * 保存角色以及对应的权限ID
     *
     * @param roleId           角色ID
     * @param permissionIdList 权限ID列表
     */
    void saveRolePermission(@Param("roleId") Long roleId,
                            @Param("permissionIdList") List<Integer> permissionIdList);
}