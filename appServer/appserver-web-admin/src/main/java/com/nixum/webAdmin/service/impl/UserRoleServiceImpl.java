package com.nixum.webAdmin.service.impl;

import com.nixum.common.service.AbstractIService;
import com.nixum.model.dao.auth.UserRoleMapper;
import com.nixum.model.model.auth.User;
import com.nixum.model.model.auth.UserRole;
import com.nixum.webAdmin.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;

/**
 * @author 233moutian
 * @date 2018/08/13
 * 用户角色业务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends AbstractIService<UserRole> implements IUserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void updateUserRole(final User user) {
        final Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andCondition("user_id = ", user.getId());
        final UserRole userRole = new UserRole()
                .setUserId(user.getId())
                .setRoleId(user.getRoleId());
        this.userRoleMapper.updateByConditionSelective(userRole, condition);
    }
}
