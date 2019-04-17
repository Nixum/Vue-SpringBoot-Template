package com.nixum.webAdmin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nixum.webAdmin.service.IRoleService;
import com.nixum.common.response.Result;
import com.nixum.common.response.ResultGenerator;
import com.nixum.model.model.auth.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 * 后台角色控制器
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    @PreAuthorize("hasAuthority('role:add')")
    @PostMapping
    public Result add(@RequestBody final Role role) {
        this.roleService.save(role);
        return ResultGenerator.genOkResult();
    }

    @PreAuthorize("hasAuthority('role:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable final Long id) {
        this.roleService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PreAuthorize("hasAuthority('role:update')")
    @PutMapping
    public Result update(@RequestBody final Role role) {
        this.roleService.update(role);
        return ResultGenerator.genOkResult();
    }

    @PreAuthorize("hasAuthority('role:list')")
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") final Integer page,
                       @RequestParam(defaultValue = "0") final Integer size) {
        PageHelper.startPage(page, size);
        final List<Role> list = this.roleService.getAllRoleWithPermission();
        final PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
