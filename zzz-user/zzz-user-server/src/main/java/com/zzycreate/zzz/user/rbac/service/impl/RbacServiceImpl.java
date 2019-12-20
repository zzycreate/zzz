package com.zzycreate.zzz.user.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzycreate.zf.core.exception.ServiceException;
import com.zzycreate.zf.feign.IdApi;
import com.zzycreate.zzz.user.common.constants.IdEnums;
import com.zzycreate.zzz.user.rbac.bo.PermBO;
import com.zzycreate.zzz.user.rbac.bo.RoleBO;
import com.zzycreate.zzz.user.rbac.bo.RoleCreateBO;
import com.zzycreate.zzz.user.rbac.bo.RoleUpdateBO;
import com.zzycreate.zzz.user.rbac.mapper.UserRoleMapper;
import com.zzycreate.zzz.user.rbac.mapper.PermMapper;
import com.zzycreate.zzz.user.rbac.mapper.RoleMapper;
import com.zzycreate.zzz.user.rbac.mapper.RolePermMapper;
import com.zzycreate.zzz.user.rbac.mapstruct.PermMapping;
import com.zzycreate.zzz.user.rbac.mapstruct.RoleMapping;
import com.zzycreate.zzz.user.rbac.po.UserRole;
import com.zzycreate.zzz.user.rbac.po.Perm;
import com.zzycreate.zzz.user.rbac.po.Role;
import com.zzycreate.zzz.user.rbac.po.RolePerm;
import com.zzycreate.zzz.user.rbac.service.RbacService;
import com.zzycreate.zzz.utils.Jsr303Utils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhenyao.zhao
 * @date 2019/11/25
 */
@Service
public class RbacServiceImpl implements RbacService {

    private static final String DEFAULT_ROLE_CUST_NO = "SYSTEM";

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermMapper rolePermMapper;
    @Resource
    private PermMapper permMapper;

    @Resource
    private IService<UserRole> bindingRoleServiceMapper;

    @Resource
    private IdApi idApi;

    @Override
    public void bindPerm(Long roleId, List<Long> permIdList) {
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        if (CollectionUtils.isEmpty(permIdList)) {
            throw new ServiceException("权限ID不能为空");
        }
        // 移除perm表中不存在的permId
        List<Perm> permDos =
                this.permMapper.selectList(Wrappers.<Perm>lambdaQuery().in(Perm::getPermId, permIdList));
        List<Long> permIdInDbp = permDos.stream().map(Perm::getPermId).collect(Collectors.toList());
        List<Long> filter1 = permIdList.stream().filter(permIdInDbp::contains).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filter1)) {
            return;
        }

        // 移除role_perm表中存在的permId
        List<RolePerm> rolePermDos = this.rolePermMapper.selectList(Wrappers.<RolePerm>lambdaQuery()
                .eq(RolePerm::getRoleId, roleId).in(RolePerm::getPermId, filter1));
        List<Long> permIdInDbrp = rolePermDos.stream().map(RolePerm::getPermId).collect(Collectors.toList());
        List<Long> filter2 = filter1.stream().filter(id -> !permIdInDbrp.contains(id)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filter2)) {
            return;
        }

        // 绑定角色权限
        List<RolePerm> rolePermList = filter2.stream().map(permId -> RolePerm.builder()
                .rolePermId(this.idApi.getId(IdEnums.ROLE_PERM_ID.getKey()))
                .roleId(roleId).permId(permId).build()).collect(Collectors.toList());
        rolePermList.forEach(rp -> this.rolePermMapper.insert(rp));

    }

    @Override
    public void unBindPerm(Long roleId, List<Long> permIdList) {
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        if (CollectionUtils.isEmpty(permIdList)) {
            throw new ServiceException("权限ID不能为空");
        }
        this.rolePermMapper.delete(Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, roleId)
                .in(RolePerm::getPermId, permIdList));
    }

    @Override
    public void emptyPerm(Long roleId) {
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        this.rolePermMapper.delete(Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, roleId));
    }

    @Override
    public void reBindPerm(Long roleId, List<Long> permIdList) {
        this.emptyPerm(roleId);
        this.bindPerm(roleId, permIdList);
    }

    @Override
    public PermBO getPerm(Long permId) {
        Perm permDo = this.permMapper.selectOne(Wrappers.<Perm>lambdaQuery().eq(Perm::getPermId, permId));
        return permDo == null ? null : PermMapping.INSTANCE.to(permDo);
    }

    @Override
    public List<PermBO> getPerms(List<Long> permIds) {
        if (CollectionUtils.isEmpty(permIds)) {
            return new ArrayList<>();
        }
        List<Perm> permDos = this.permMapper.selectList(Wrappers.<Perm>lambdaQuery().in(Perm::getPermId, permIds));
        return CollectionUtils.isEmpty(permDos) ? new ArrayList<>() : PermMapping.INSTANCE.to(permDos);
    }

    @Override
    public List<PermBO> getAllPerms() {
        List<Perm> permDos = this.permMapper.selectList(Wrappers.<Perm>lambdaQuery());
        return CollectionUtils.isEmpty(permDos) ? new ArrayList<>() : PermMapping.INSTANCE.to(permDos);
    }

    @Override
    public Map<Long, PermBO> getPermMap(List<Long> permIds) {
        List<PermBO> perms = this.getPerms(permIds);
        return perms.stream().collect(Collectors.toMap(PermBO::getPermId, Function.identity(), (a, b) -> a));
    }

    @Override
    public List<PermBO> getPermsByRoleId(Long roleId) {
        if (roleId == null) {
            return new ArrayList<>();
        }
        Map<Long, List<PermBO>> permMapByRoleIds = this.getPermMapByRoleIds(Collections.singletonList(roleId));
        return permMapByRoleIds.getOrDefault(roleId, new ArrayList<>());
    }

    @Override
    public Map<Long, List<PermBO>> getPermMapByRoleIds(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new HashMap<>(16);
        }
        List<RolePerm> rolePermDos =
                this.rolePermMapper.selectList(Wrappers.<RolePerm>lambdaQuery().in(RolePerm::getRoleId, roleIdList));
        Map<Long, List<Long>> rolePermMap = rolePermDos.stream().collect(Collectors.groupingBy(
                RolePerm::getRoleId, Collectors.mapping(RolePerm::getPermId, Collectors.toList())));

        List<Long> permIds = rolePermDos.stream().map(RolePerm::getPermId).collect(Collectors.toList());
        Map<Long, PermBO> permMap = this.getPermMap(permIds);
        Map<Long, List<PermBO>> result = new HashMap<>(16);
        rolePermMap.forEach((roleId, permIdList) -> {
            List<PermBO> tmpPerms =
                    permIdList.stream().map(permMap::get).filter(Objects::nonNull).collect(Collectors.toList());
            result.put(roleId, tmpPerms);
        });

        return result;
    }

    @Override
    public RoleBO addRole(RoleCreateBO roleCreate) {
        Jsr303Utils.validate(roleCreate);

        Role roleDo = RoleMapping.INSTANCE.createToDo(roleCreate);
        roleDo.setRoleId(this.idApi.getId(IdEnums.ROLE_ID.getKey()));
        this.roleMapper.insert(roleDo);

        return this.getRole(roleDo.getRoleId());
    }

    @Override
    public RoleBO updateRole(Long roleId, RoleUpdateBO roleUpdate) {
        if (StringUtils.isEmpty(roleUpdate.getRoleName()) && StringUtils.isEmpty(roleUpdate.getRoleCode())
                && StringUtils.isEmpty(roleUpdate.getRoleDescription())) {
            throw new ServiceException("没有检查到需要更新的内容");
        }

        LambdaUpdateWrapper<Role> wrapper = Wrappers.<Role>lambdaUpdate();
        if (StringUtils.isNotEmpty(roleUpdate.getRoleName())) {
            wrapper.set(Role::getRoleName, roleUpdate.getRoleName());
        }
        if (StringUtils.isNotEmpty(roleUpdate.getRoleCode())) {
            wrapper.set(Role::getRoleCode, roleUpdate.getRoleCode());
        }
        if (StringUtils.isNotEmpty(roleUpdate.getRoleDescription())) {
            wrapper.set(Role::getRoleDescription, roleUpdate.getRoleDescription());
        }

        wrapper.eq(Role::getRoleId, roleId);
        this.roleMapper.update(null, wrapper);

        return this.getRole(roleId);
    }

    @Override
    public void deleteRole(Long roleId) {
        if (roleId == null) {
            return;
        }
        this.userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId, roleId));
        this.roleMapper.delete(Wrappers.<Role>lambdaQuery().eq(Role::getRoleId, roleId));
        this.rolePermMapper.delete(Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, roleId));
    }

    @Override
    public void bindRole(Long bindId, List<Long> roleIdList) {
        if (bindId == null) {
            throw new ServiceException("服务关系ID不能为空");
        }
        if (CollectionUtils.isEmpty(roleIdList)) {
            throw new ServiceException("角色ID不能为空");
        }

        // 移除role表中不存在的roleId
        List<Role> roleDos =
                this.roleMapper.selectList(Wrappers.<Role>lambdaQuery().in(Role::getRoleId, roleIdList));
        List<Long> roleIdInDbr = roleDos.stream().map(Role::getRoleId).collect(Collectors.toList());
        List<Long> filter1 = roleIdList.stream().filter(roleIdInDbr::contains).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filter1)) {
            return;
        }

        // 移除role_perm表中存在的permId
        List<UserRole> userRoleDos = this.userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getBindId, bindId).in(UserRole::getRoleId, filter1));
        List<Long> roleIdInDbbr = userRoleDos.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Long> filter2 = filter1.stream().filter(id -> !roleIdInDbbr.contains(id)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filter2)) {
            return;
        }

        // 绑定角色权限
        List<UserRole> userRoleList = filter2.stream().map(roleId -> UserRole.builder()
                .userRoleId(this.idApi.getId(IdEnums.USER_ROLE_ID.getKey()))
                .bindId(bindId).roleId(roleId).build()).collect(Collectors.toList());
        this.bindingRoleServiceMapper.saveBatch(userRoleList);

    }

    @Override
    public void unBindRole(Long bindId, List<Long> roleIdList) {
        if (bindId == null) {
            throw new ServiceException("服务关系ID不能为空");
        }
        if (CollectionUtils.isEmpty(roleIdList)) {
            throw new ServiceException("角色ID不能为空");
        }
        this.userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getBindId, bindId)
                .in(UserRole::getRoleId, roleIdList));
    }

    @Override
    public void emptyRole(Long bindId) {
        if (bindId == null) {
            throw new ServiceException("服务关系ID不能为空");
        }
        this.userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getBindId, bindId));
    }

    @Override
    public void reBindRole(Long bindId, List<Long> roleIdList) {
        this.emptyRole(bindId);
        this.bindRole(bindId, roleIdList);
    }

    @Override
    public RoleBO getRole(Long roleId) {
        Role roleDO = this.roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleId, roleId));
        if (roleDO == null) {
            return null;
        }

        RoleBO bo = RoleMapping.INSTANCE.to(roleDO);

        // 查询 RolePerm
        List<RolePerm> rolePerms = this.rolePermMapper.selectList(
                Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, roleDO.getRoleId()));
        List<Long> permIds = rolePerms.stream().map(RolePerm::getPermId).distinct().collect(Collectors.toList());

        // 查询 Perm
        if (CollectionUtils.isNotEmpty(permIds)) {
            List<PermBO> perms = this.getPerms(permIds);
            bo.setPerms(perms);
            bo.setPermCodes(perms.stream().map(PermBO::getPermCode).collect(Collectors.toList()));
            bo.setPermIds(perms.stream().map(PermBO::getPermId).collect(Collectors.toList()));
        }

        return bo;
    }

    @Override
    public List<RoleBO> getRoles(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        List<Role> roleDos = this.roleMapper.selectList(Wrappers.<Role>lambdaQuery().in(Role::getRoleId, roleIds));
        List<RoleBO> roleBos = RoleMapping.INSTANCE.to(roleDos);

        Map<Long, List<PermBO>> permMapByRoleIds = this.getPermMapByRoleIds(roleIds);

        roleBos.forEach(bo -> {
            Long roleId = bo.getRoleId();
            List<PermBO> perms = permMapByRoleIds.getOrDefault(roleId, new ArrayList<>());
            bo.setPerms(perms);
            bo.setPermCodes(perms.stream().map(PermBO::getPermCode).collect(Collectors.toList()));
            bo.setPermIds(perms.stream().map(PermBO::getPermId).collect(Collectors.toList()));
        });

        return roleBos;
    }

    @Override
    public Map<Long, RoleBO> getRoleMap(List<Long> roleIds) {
        List<RoleBO> roles = this.getRoles(roleIds);
        return roles.stream().collect(Collectors.toMap(RoleBO::getRoleId, Function.identity(), (a, b) -> a));
    }

    @Override
    public List<RoleBO> getRolesByBindIds(Long bindId) {
        Map<Long, List<RoleBO>> roleMapByBindIds = this.getRoleMapByBindIds(Collections.singletonList(bindId));
        return roleMapByBindIds.getOrDefault(bindId, new ArrayList<>());
    }

    @Override
    public Map<Long, List<RoleBO>> getRoleMapByBindIds(List<Long> bindIdList) {
        if (CollectionUtils.isEmpty(bindIdList)) {
            return new HashMap<>(16);
        }

        List<UserRole> userRoleDos = this.userRoleMapper.selectList(
                Wrappers.<UserRole>lambdaQuery().in(UserRole::getBindId, bindIdList));
        Map<Long, List<Long>> bindRoleMap = userRoleDos.stream().collect(
                Collectors.groupingBy(
                        UserRole::getBindId, Collectors.mapping(UserRole::getRoleId, Collectors.toList())
                ));
        List<Long> roleIds =
                userRoleDos.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList());

        Map<Long, RoleBO> roleMap = this.getRoleMap(roleIds);
        Map<Long, List<RoleBO>> result = new HashMap<>(16);
        bindRoleMap.forEach((bindId, roleIdList) -> {
            List<RoleBO> tmpRoles =
                    roleIdList.stream().map(roleMap::get).filter(Objects::nonNull).collect(Collectors.toList());
            result.put(bindId, tmpRoles);
        });

        return result;
    }

}
