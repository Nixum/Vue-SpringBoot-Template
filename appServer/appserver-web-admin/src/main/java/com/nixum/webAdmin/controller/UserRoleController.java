package com.nixum.webAdmin.controller;

import com.nixum.webAdmin.service.IUserRoleService;
import com.nixum.common.response.Result;
import com.nixum.common.response.ResultGenerator;
import com.nixum.model.model.auth.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zoctan
 * @date 2018/06/09
 * 后台用户角色控制器
 */
@RestController
@RequestMapping("/user/role")
public class UserRoleController {
    @Resource
    private IUserRoleService userRoleService;

    @PreAuthorize("hasAuthority('role:update')")
    @PutMapping
    public Result updateUserRole(@RequestBody final User user) {
        this.userRoleService.updateUserRole(user);
        return ResultGenerator.genOkResult();
    }
}
