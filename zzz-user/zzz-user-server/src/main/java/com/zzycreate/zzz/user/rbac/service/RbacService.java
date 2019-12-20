package com.zzycreate.zzz.user.rbac.service;

import com.zzycreate.zzz.user.rbac.bo.PermBO;
import com.zzycreate.zzz.user.rbac.bo.PermCreateBO;
import com.zzycreate.zzz.user.rbac.bo.PermUpdateBO;
import com.zzycreate.zzz.user.rbac.bo.RoleBO;
import com.zzycreate.zzz.user.rbac.bo.RoleCreateBO;
import com.zzycreate.zzz.user.rbac.bo.RoleUpdateBO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author zhenyao.zhao
 * @date 2019/11/25
 */
public interface RbacService {

    // ---------------- permission ----------------

    /**
     * 新增权限项
     *
     * @param permCreate 新增的权限信息
     * @return PermBO
     */
    PermBO addPerm(@Valid PermCreateBO permCreate);

    /**
     * 更新权限项
     *
     * @param permId     权限ID
     * @param permUpdate 更新的权限信息
     * @return PermBO
     */
    PermBO updatePerm(@Valid @NotNull(message = "权限ID不能为空") Long permId, PermUpdateBO permUpdate);

    /**
     * 移除权限项
     *
     * @param permId 待移除的权限项ID
     */
    void deletePerm(Long permId);

    /**
     * 查询权限信息
     *
     * @param permId 权限ID
     * @return PermBO 权限信息
     */
    PermBO getPerm(Long permId);

    /**
     * 查询权限项
     *
     * @param permName 权限名
     * @param permCode 权限编号
     * @return 权限项
     */
    PermBO getPerm(String permName, String permCode);

    /**
     * 查询权限信息
     *
     * @param permIds 权限ID
     * @return List<PermBO> 权限信息
     */
    List<PermBO> getPerms(List<Long> permIds);

    /**
     * 查询所有的权限信息
     *
     * @return 所有的权限信息
     */
    List<PermBO> getAllPerms();

    /**
     * 查询权限信息
     *
     * @param permIds 权限ID
     * @return <code>Map<Long, PermBO></code>
     */
    Map<Long, PermBO> getPermMap(List<Long> permIds);

    // ---------------- role_permission ----------------

    /**
     * 给角色绑定指定的权限关系
     *
     * @param roleId     角色ID
     * @param permIdList 待绑定的权限ID
     */
    void bindPerm(@Valid @NotNull(message = "角色ID不能为空") Long roleId,
                  @Valid @NotEmpty(message = "权限ID不能为空") List<Long> permIdList);

    /**
     * 给角色解绑指定的权限关系
     *
     * @param roleId     角色ID
     * @param permIdList 待解绑的权限ID
     */
    void unBindPerm(@Valid @NotNull(message = "角色ID不能为空") Long roleId,
                    @Valid @NotEmpty(message = "权限ID不能为空") List<Long> permIdList);

    /**
     * 清空角色的权限绑定关系
     *
     * @param roleId 角色ID
     */
    void emptyPerm(@Valid @NotNull(message = "角色ID不能为空") Long roleId);

    /**
     * 重新给角色指定的权限关系，会首先清除绑定权限
     *
     * @param roleId     角色ID
     * @param permIdList 待绑定的权限ID
     */
    void reBindPerm(@Valid @NotNull(message = "角色ID不能为空") Long roleId,
                    @Valid @NotEmpty(message = "权限ID不能为空") List<Long> permIdList);

    /**
     * 查询角色的权限信息
     *
     * @param roleId 角色ID
     * @return List<PermBO>
     */
    List<PermBO> getPermsByRoleId(Long roleId);

    /**
     * 查询角色的权限
     *
     * @param roleIdList 角色ID
     * @return <code>Map<Long, List<PermBO>></code> 权限信息 key为roleId
     */
    Map<Long, List<PermBO>> getPermMapByRoleIds(List<Long> roleIdList);

    /**
     * 检查角色是否拥有指定权限
     *
     * @param roleId 角色ID
     * @param permId 权限ID
     * @return boolean
     */
    boolean hasPermForRole(@Valid @NotNull(message = "角色ID不能为空") Long roleId,
                           @Valid @NotNull(message = "权限ID不能为空") Long permId);

    /**
     * 检查角色是否拥有指定权限
     *
     * @param roleId   角色ID
     * @param permName 权限名
     * @param permCode 权限编码
     * @return boolean
     */
    boolean hasPermForRole(@Valid @NotNull(message = "角色ID不能为空") Long roleId, String permName, String permCode);

    // ---------------- role ----------------

    /**
     * 新增角色信息
     *
     * @param roleCreate 新增的角色信息
     * @return RoleBO
     */
    RoleBO addRole(@Valid RoleCreateBO roleCreate);

    /**
     * 更新角色信息
     *
     * @param roleId     角色ID
     * @param roleUpdate 更新的角色信息
     * @return RoleBO
     */
    RoleBO updateRole(@Valid @NotNull(message = "角色ID不能为空") Long roleId, RoleUpdateBO roleUpdate);

    /**
     * 移除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(Long roleId);

    /**
     * 查询角色信息
     *
     * @param roleId 角色ID
     * @return 查询角色信息
     */
    RoleBO getRole(Long roleId);

    /**
     * 查询角色信息
     *
     * @param roleName 角色名
     * @param roleCode 角色编号
     * @return 查询角色信息
     */
    RoleBO getRole(String roleName, String roleCode);

    /**
     * 查询角色信息
     *
     * @param roleIds 角色ID
     * @return List<PermBO> 角色信息
     */
    List<RoleBO> getRoles(List<Long> roleIds);

    /**
     * 查询角色信息
     *
     * @param roleIds 角色ID
     * @return <code>Map<Long, RoleBO></code> 角色集合
     */
    Map<Long, RoleBO> getRoleMap(List<Long> roleIds);

    // ---------------- user_role ----------------

    /**
     * 新增角色绑定
     *
     * @param userId     用户ID
     * @param roleIdList 带绑定的角色ID
     */
    void bindRole(@Valid @NotNull(message = "用户ID不能为空") Long userId,
                  @Valid @NotEmpty(message = "角色ID不能为空") List<Long> roleIdList);

    /**
     * 解除角色绑定
     *
     * @param userId     用户ID
     * @param roleIdList 带解绑的角色ID
     */
    void unBindRole(@Valid @NotNull(message = "用户ID不能为空") Long userId,
                    @Valid @NotEmpty(message = "角色ID不能为空") List<Long> roleIdList);

    /**
     * 清空用户的角色绑定关系
     *
     * @param userId 用户ID
     */
    void emptyRole(Long userId);

    /**
     * 重新给用户指定的权限关系，会首先清除绑定权限
     *
     * @param userId     用户ID
     * @param roleIdList 带绑定的角色ID
     */
    void reBindRole(@Valid @NotNull(message = "用户ID不能为空") Long userId,
                    @Valid @NotEmpty(message = "角色ID不能为空") List<Long> roleIdList);

    /**
     * 查询用户的角色
     *
     * @param userId 用户ID
     * @return List<RoleBO>
     */
    List<RoleBO> getRolesByUserIds(Long userId);

    /**
     * 查询用户的角色
     *
     * @param userIdList 用户ID
     * @return <code>Map<Long, List<RoleBO>></code> key为user_id
     */
    Map<Long, List<RoleBO>> getRoleMapByUserIds(List<Long> userIdList);

    /**
     * 检查用户是否拥有指定角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return boolean
     */
    boolean hasRoleForUser(@Valid @NotNull(message = "用户ID不能为空") Long userId,
                           @Valid @NotNull(message = "角色ID不能为空") Long roleId);

    /**
     * 检查用户是否拥有指定角色
     *
     * @param userId   用户ID
     * @param roleName 角色名
     * @param roleCode 角色编码
     * @return boolean
     */
    boolean hasRoleForUser(@Valid @NotNull(message = "用户ID不能为空") Long userId, String roleName, String roleCode);

    /**
     * 检查用户是否拥有指定权限
     *
     * @param userId 用户ID
     * @param permId 权限ID
     * @return boolean
     */
    boolean hasPermForUser(@Valid @NotNull(message = "用户ID不能为空") Long userId,
                           @Valid @NotNull(message = "角色ID不能为空") Long permId);

    /**
     * 检查用户是否拥有指定权限
     *
     * @param userId   用户ID
     * @param permName 权限名
     * @param permCode 权限编码
     * @return boolean
     */
    boolean hasPermForUser(@Valid @NotNull(message = "用户ID不能为空") Long userId, String permName, String permCode);


}
