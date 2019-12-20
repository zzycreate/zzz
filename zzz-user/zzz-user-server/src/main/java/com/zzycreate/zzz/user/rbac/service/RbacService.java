package com.zzycreate.zzz.user.rbac.service;

import com.zzycreate.zzz.user.rbac.bo.PermBO;
import com.zzycreate.zzz.user.rbac.bo.RoleBO;
import com.zzycreate.zzz.user.rbac.bo.RoleCreateBO;
import com.zzycreate.zzz.user.rbac.bo.RoleUpdateBO;

import java.util.List;
import java.util.Map;

/**
 * @author zhenyao.zhao
 * @date 2019/11/25
 */
public interface RbacService {

    /**
     * 给角色绑定指定的权限关系
     *
     * @param roleId     角色ID
     * @param permIdList 待绑定的权限ID
     */
    void bindPerm(Long roleId, List<Long> permIdList);

    /**
     * 给角色解绑指定的权限关系
     *
     * @param roleId     角色ID
     * @param permIdList 待解绑的权限ID
     */
    void unBindPerm(Long roleId, List<Long> permIdList);

    /**
     * 清空角色的权限绑定关系
     *
     * @param roleId 角色ID
     */
    void emptyPerm(Long roleId);

    /**
     * 重新给角色指定的权限关系，会首先清除绑定权限
     *
     * @param roleId     角色ID
     * @param permIdList 待解绑的权限ID
     */
    void reBindPerm(Long roleId, List<Long> permIdList);

    /**
     * 查询权限信息
     *
     * @param permId 权限ID
     * @return PermBO 权限信息
     */
    PermBO getPerm(Long permId);

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
     * 新增角色(仅增加角色，不增加权限绑定关系)
     *
     * @param roleCreate 新增的角色信息
     * @return RoleBO
     */
    RoleBO addRole(RoleCreateBO roleCreate);

    /**
     * 更新角色信息（仅更新角色信息）
     *
     * @param roleId     角色ID
     * @param roleUpdate 更新的角色信息
     * @return RoleBO
     */
    RoleBO updateRole(Long roleId, RoleUpdateBO roleUpdate);

    /**
     * 移除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(Long roleId);

    /**
     * 新增角色绑定
     *
     * @param bindId     人员ID
     * @param roleIdList 带绑定的角色ID
     */
    void bindRole(Long bindId, List<Long> roleIdList);

    /**
     * 解除角色绑定
     *
     * @param bindId     绑定关系
     * @param roleIdList 带解绑的角色ID
     */
    void unBindRole(Long bindId, List<Long> roleIdList);

    /**
     * 清空人员的角色绑定关系
     *
     * @param bindId 人员ID
     */
    void emptyRole(Long bindId);

    /**
     * 重新给雇员指定的权限关系，会首先清除绑定权限
     *
     * @param bindId     人员ID
     * @param roleIdList 待处理的角色ID
     */
    void reBindRole(Long bindId, List<Long> roleIdList);

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

    /**
     * 查询角色的权限
     *
     * @param bindId 人员ID
     * @return <code>Map<Long, List<RoleBO>></code> key为binding_id
     */
    List<RoleBO> getRolesByBindIds(Long bindId);

    /**
     * 查询角色的权限
     *
     * @param bindIdList 人员ID
     * @return <code>Map<Long, List<RoleBO>></code> key为binding_id
     */
    Map<Long, List<RoleBO>> getRoleMapByBindIds(List<Long> bindIdList);

}
