package com.nixum.webAdmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nixum.webAdmin.service.IPermissionService;
import com.nixum.common.response.Result;
import com.nixum.common.response.ResultGenerator;
import com.nixum.model.model.auth.Permission;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 * 权限控制器
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private IPermissionService permissionService;

    @PreAuthorize("hasAuthority('role:list')")
    @GetMapping
    public Result listResourcePermission(@RequestParam(defaultValue = "0") final Integer page,
                                         @RequestParam(defaultValue = "0") final Integer size) {
        PageHelper.startPage(page, size);
        final List<JSONObject> list = this.permissionService.findAllResourcePermission();
        //noinspection unchecked
        final PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PreAuthorize("hasAuthority('permission:list')")
    @GetMapping("listPermission")
    public Result listPermission(@RequestParam(defaultValue = "0") final Integer page,
                                         @RequestParam(defaultValue = "0") final Integer size) {
        PageHelper.startPage(page, size);
        final List<Permission> list = this.permissionService.findAll();
        //noinspection unchecked
        final PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PreAuthorize("hasAuthority('permission:add')")
    @PostMapping
    public Result add(@RequestBody final Permission permission) {
        this.permissionService.save(permission);
        return ResultGenerator.genOkResult();
    }

    @PreAuthorize("hasAuthority('permission:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable final Long id) {
        this.permissionService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PreAuthorize("hasAuthority('permission:update')")
    @PutMapping
    public Result update(@RequestBody final Permission permission) {
        this.permissionService.update(permission);
        return ResultGenerator.genOkResult();
    }

}
