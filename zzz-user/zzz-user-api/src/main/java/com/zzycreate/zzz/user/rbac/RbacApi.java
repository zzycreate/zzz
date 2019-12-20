package com.zzycreate.zzz.user.rbac;

import com.zzycreate.zf.core.model.Result;
import com.zzycreate.zzz.user.common.SystemConstants;
import com.zzycreate.zzz.user.rbac.bo.PermBO;
import com.zzycreate.zzz.user.rbac.bo.RoleBO;
import com.zzycreate.zzz.user.rbac.bo.RoleCreateBO;
import com.zzycreate.zzz.user.rbac.bo.RoleUpdateBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * RBAC 角色权限
 *
 * @author zhenyao.zhao
 * @date 2019/11/20
 */
@FeignClient(
        value = SystemConstants.SERVER_NAME,
        contextId = SystemConstants.CONTEXT_RBAC,
        url = "${feignurl.custworkbench}",
        path = SystemConstants.PATH_RBAC
)
@Api(value = SystemConstants.CONTEXT_RBAC, tags = "角色权限")
public interface RbacApi {

    // -------------------------- 权限 --------------------------

    /**
     * 获取权限
     * GET /perms/1
     *
     * @param permId 权限ID
     * @return Result 权限
     */
    @GetMapping("/perms/{permId}")
    @ApiOperation(value = "获取权限")
    Result<PermBO> getPerm(@PathVariable Long permId);

    /**
     * 获取所有的权限
     * GET /perms
     *
     * @return Result 所有权限
     */
    @GetMapping("/perms")
    @ApiOperation(value = "获取所有的权限")
    Result<List<PermBO>> getAllPerm();

    /**
     * 获取权限(通过 permId 批量获取)
     * POST /perms/batch/get/permIds
     * [1, 2, 3]
     *
     * @param permIds 权限ID
     * @return Result 权限
     */
    @PostMapping("/perms/batch/get/permIds")
    @ApiOperation(value = "获取权限(通过 permId 批量获取)")
    Result<List<PermBO>> getPermList(@RequestBody List<Long> permIds);

    // -------------------------- 角色 --------------------------

    /**
     * 获取角色的权限
     * GET /roles/1/perms
     *
     * @param roleId 角色ID
     * @return Result 权限
     */
    @GetMapping("/roles/{roleId}/perms")
    @ApiOperation(value = "获取权限(通过 roleId 获取)")
    Result<List<PermBO>> getPermByRoleId(@PathVariable Long roleId);

    /**
     * 获取角色
     * GET /roles/1
     *
     * @param roleId 角色ID
     * @return Result 权限
     */
    @GetMapping("/roles/{roleId}")
    @ApiOperation(value = "获取角色")
    Result<RoleBO> getRole(@PathVariable Long roleId);

    /**
     * 获取权限
     * POST /roles/batch/get/roleIds
     * [1, 2, 3]
     *
     * @param roleIds 权限ID
     * @return Result 权限
     */
    @PostMapping("/roles/batch/get/roleIds")
    @ApiOperation(value = "获取角色(通过 roleId 批量获取)")
    Result<List<RoleBO>> getRoleList(@RequestBody List<Long> roleIds);

    /**
     * 新增角色
     * POST /roles
     * {"roleName": "角色名", "roleCode": "角色编号", "roleDescription": "角色描述", "defaultRole": 1, "custNo": "角色所属客户"}
     *
     * @param role 角色信息
     * @return Result 权限
     */
    @PostMapping("/roles")
    @ApiOperation(value = "新增角色")
    Result<RoleBO> addRole(@RequestBody RoleCreateBO role);

    /**
     * 更新角色
     * PUT /roles/1
     * {"roleId": 1, "roleName": "角色名", "roleCode": "角色编号", "roleDescription": "角色描述"}
     *
     * @param roleId 角色ID
     * @param role   更新角色信息
     * @return Result 权限
     */
    @PutMapping("/roles/{roleId}")
    @ApiOperation(value = "更新角色")
    Result<RoleBO> updateRole(@PathVariable Long roleId, @RequestBody RoleUpdateBO role);

    /**
     * 删除角色
     * DELETE /roles/1
     *
     * @param roleId 角色ID
     * @return Result 权限
     */
    @DeleteMapping("/roles/{roleId}")
    @ApiOperation(value = "删除角色")
    Result<Void> deleteRole(@PathVariable Long roleId);


    /**
     * 给角色绑定权限
     * PUT /roles/bind/1
     * [1, 2, 3]
     *
     * @param roleId  角色ID
     * @param permIds 权限ID
     * @return Result
     */
    @PutMapping("/roles/bind/{roleId}")
    @ApiOperation(value = "给角色绑定权限")
    Result<Void> bindPerm(@PathVariable Long roleId, @RequestBody List<Long> permIds);

    /**
     * 给角色解绑权限
     * PUT /roles/unbind/1
     * [1, 2, 3]
     *
     * @param roleId  角色ID
     * @param permIds 权限ID
     * @return Result
     */
    @PutMapping("/roles/unbind/{roleId}")
    @ApiOperation(value = "给角色解绑权限")
    Result<Void> unBindPerm(@PathVariable Long roleId, @RequestBody List<Long> permIds);

    /**
     * 给角色清空权限
     * PUT /roles/empty/1
     * [1, 2, 3]
     *
     * @param roleId 角色ID
     * @return Result
     */
    @PutMapping("/roles/empty/{roleId}")
    @ApiOperation(value = "给角色清空权限")
    Result<Void> emptyPerm(@PathVariable Long roleId);

    /**
     * 给角色重新绑定权限
     * PUT /roles/rebind/1
     * [1, 2, 3]
     *
     * @param roleId  角色ID
     * @param permIds 权限ID
     * @return Result
     */
    @PutMapping("/roles/rebind/{roleId}")
    @ApiOperation(value = "给角色重新绑定权限")
    Result<Void> reBindPerm(@PathVariable Long roleId, @RequestBody List<Long> permIds);

    // -------------------------- 服务关系 --------------------------

    /**
     * 获取服务关系的角色
     * GET /bindings/1/roles
     *
     * @param bindingId 角色ID
     * @return Result 权限
     */
    @GetMapping("/bindings/{bindingId}/roles")
    @ApiOperation(value = "获取服务关系的角色")
    Result<List<RoleBO>> getRolesByBindingId(@PathVariable Long bindingId);

    /**
     * 给服务关系绑定角色
     * PUT /bindings/bind/1
     *
     * @param bindingId 服务关系ID
     * @param roleIds   角色ID
     * @return Result
     */
    @PutMapping("/bindings/bind/{bindingId}")
    @ApiOperation(value = "给服务关系绑定角色")
    Result<Void> bindRole(@PathVariable Long bindingId, @RequestBody List<Long> roleIds);

    /**
     * 给服务关系解绑角色
     * PUT /bindings/unbind/1
     *
     * @param bindingId 服务关系ID
     * @param roleIds   角色ID
     * @return Result
     */
    @PutMapping("/bindings/unbind/{bindingId}")
    @ApiOperation(value = "给服务关系解绑角色")
    Result<Void> unBindRole(@PathVariable Long bindingId, @RequestBody List<Long> roleIds);

    /**
     * 给服务关系清空角色
     * PUT /bindings/empty/1
     *
     * @param bindingId 服务关系ID
     * @return Result
     */
    @PutMapping("/bindings/empty/{bindingId}")
    @ApiOperation(value = "给服务关系清空角色")
    Result<Void> emptyRole(@PathVariable Long bindingId);

    /**
     * 给服务关系重新绑定角色
     * PUT /bindings/rebind/1
     *
     * @param bindingId 服务关系ID
     * @param roleIds   角色ID
     * @return Result
     */
    @PutMapping("/bindings/rebind/{bindingId}")
    @ApiOperation(value = "给服务关系重新绑定角色")
    Result<Void> reBindRole(@PathVariable Long bindingId, @RequestBody List<Long> roleIds);

}
