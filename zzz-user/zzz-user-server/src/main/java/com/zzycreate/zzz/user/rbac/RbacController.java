package com.zzycreate.zzz.user.rbac;

import com.zzycreate.zf.core.model.Result;
import com.zzycreate.zzz.user.common.SystemConstants;
import com.zzycreate.zzz.user.rbac.bo.PermBO;
import com.zzycreate.zzz.user.rbac.bo.RoleBO;
import com.zzycreate.zzz.user.rbac.bo.RoleCreateBO;
import com.zzycreate.zzz.user.rbac.bo.RoleUpdateBO;
import com.zzycreate.zzz.user.rbac.service.RbacService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zzycreate
 * @date 2019/12/18
 */
@RestController
@RequestMapping(SystemConstants.PATH_RBAC)
@Api(value = SystemConstants.CONTEXT_RBAC, tags = "角色权限")
public class RbacController {

    private final RbacService rbacService;

    @Autowired
    public RbacController(RbacService rbacService) {
        this.rbacService = rbacService;
    }

    @GetMapping("/perms/{permId}")
    @ApiOperation(value = "获取权限")
    public Result<PermBO> getPerm(@PathVariable Long permId) {
        PermBO perm = this.rbacService.getPerm(permId);
        return Result.success4data(perm);
    }

    @GetMapping("/perms")
    @ApiOperation(value = "获取所有的权限")
    public Result<List<PermBO>> getAllPerm() {
        List<PermBO> allPerms = this.rbacService.getAllPerms();
        return Result.success4data(allPerms);
    }

    @PostMapping("/perms/batch/get/permIds")
    @ApiOperation(value = "获取权限(通过 permId 批量获取)")
    public Result<List<PermBO>> getPermList(@RequestBody List<Long> permIds) {
        List<PermBO> perms = this.rbacService.getPerms(permIds);
        return Result.success4data(perms);
    }

    @GetMapping("/roles/{roleId}/perms")
    @ApiOperation(value = "获取权限(通过 roleId 获取)")
    public Result<List<PermBO>> getPermByRoleId(@PathVariable Long roleId) {
        List<PermBO> perms = this.rbacService.getPermsByRoleId(roleId);
        return Result.success4data(perms);
    }

    @GetMapping("/roles/{roleId}")
    @ApiOperation(value = "获取角色")
    public Result<RoleBO> getRole(@PathVariable Long roleId) {
        RoleBO role = this.rbacService.getRole(roleId);
        return Result.success4data(role);
    }

    @PostMapping("/roles/batch/get/roleIds")
    @ApiOperation(value = "获取角色(通过 roleId 批量获取)")
    public Result<List<RoleBO>> getRoleList(@RequestBody List<Long> roleIds) {
        List<RoleBO> roles = this.rbacService.getRoles(roleIds);
        return Result.success4data(roles);
    }

    @PostMapping("/roles")
    @ApiOperation(value = "新增角色")
    public Result<RoleBO> addRole(@RequestBody RoleCreateBO role) {
        RoleBO roleBo = this.rbacService.addRole(role);
        return Result.success4data(roleBo);
    }

    @PutMapping("/roles/{roleId}")
    @ApiOperation(value = "更新角色")
    public Result<RoleBO> updateRole(@PathVariable Long roleId, @RequestBody RoleUpdateBO role) {
        RoleBO roleBo = this.rbacService.updateRole(roleId, role);
        return Result.success4data(roleBo);
    }

    @DeleteMapping("/roles/{roleId}")
    @ApiOperation(value = "删除角色")
    public Result<Void> deleteRole(@PathVariable Long roleId) {
        this.rbacService.deleteRole(roleId);
        return Result.success();
    }

    @PutMapping("/roles/bind/{roleId}")
    @ApiOperation(value = "给角色绑定权限")
    public Result<Void> bindPerm(@PathVariable Long roleId, @RequestBody List<Long> permIds) {
        this.rbacService.bindPerm(roleId, permIds);
        return Result.success();
    }

    @PutMapping("/roles/unbind/{roleId}")
    @ApiOperation(value = "给角色解绑权限")
    public Result<Void> unBindPerm(@PathVariable Long roleId, @RequestBody List<Long> permIds) {
        this.rbacService.unBindPerm(roleId, permIds);
        return Result.success();
    }

    @PutMapping("/roles/empty/{roleId}")
    @ApiOperation(value = "给角色清空权限")
    public Result<Void> emptyPerm(@PathVariable Long roleId) {
        this.rbacService.emptyPerm(roleId);
        return Result.success();
    }

    @PutMapping("/roles/rebind/{roleId}")
    @ApiOperation(value = "给角色重新绑定权限")
    public Result<Void> reBindPerm(@PathVariable Long roleId, @RequestBody List<Long> permIds) {
        this.rbacService.reBindPerm(roleId, permIds);
        return Result.success();
    }

    @GetMapping("/bindings/{bindingId}/roles")
    @ApiOperation(value = "获取服务关系的角色")
    public Result<List<RoleBO>> getRolesByBindingId(@PathVariable Long bindingId) {
        List<RoleBO> roles = this.rbacService.getRolesByBindIds(bindingId);
        return Result.success4data(roles);
    }

    @PutMapping("/bindings/bind/{bindingId}")
    @ApiOperation(value = "给服务关系绑定角色")
    public Result<Void> bindRole(@PathVariable Long bindingId, @RequestBody List<Long> roleIds) {
        this.rbacService.bindRole(bindingId, roleIds);
        return Result.success();
    }

    @PutMapping("/bindings/unbind/{bindingId}")
    @ApiOperation(value = "给服务关系解绑角色")
    public Result<Void> unBindRole(@PathVariable Long bindingId, @RequestBody List<Long> roleIds) {
        this.rbacService.unBindRole(bindingId, roleIds);
        return Result.success();
    }

    @PutMapping("/bindings/empty/{bindingId}")
    @ApiOperation(value = "给服务关系清空角色")
    public Result<Void> emptyRole(@PathVariable Long bindingId) {
        this.rbacService.emptyRole(bindingId);
        return Result.success();
    }

    @PutMapping("/bindings/rebind/{bindingId}")
    @ApiOperation(value = "给服务关系重新绑定角色")
    public Result<Void> reBindRole(@PathVariable Long bindingId, @RequestBody List<Long> roleIds) {
        this.rbacService.reBindRole(bindingId, roleIds);
        return Result.success();
    }

}
