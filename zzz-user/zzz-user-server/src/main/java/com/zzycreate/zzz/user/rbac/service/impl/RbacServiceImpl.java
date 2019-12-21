package com.zzycreate.zzz.user.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzycreate.zf.core.exception.ServiceException;
import com.zzycreate.zf.feign.IdApi;
import com.zzycreate.zzz.user.common.enums.IdEnums;
import com.zzycreate.zzz.user.rbac.bo.PermBO;
import com.zzycreate.zzz.user.rbac.bo.PermCreateBO;
import com.zzycreate.zzz.user.rbac.bo.PermUpdateBO;
import com.zzycreate.zzz.user.rbac.bo.RoleBO;
import com.zzycreate.zzz.user.rbac.bo.RoleCreateBO;
import com.zzycreate.zzz.user.rbac.bo.RoleUpdateBO;
import com.zzycreate.zzz.user.rbac.mapper.PermMapper;
import com.zzycreate.zzz.user.rbac.mapper.RoleMapper;
import com.zzycreate.zzz.user.rbac.mapper.RolePermMapper;
import com.zzycreate.zzz.user.rbac.mapper.UserRoleMapper;
import com.zzycreate.zzz.user.rbac.mapstruct.PermMapstruct;
import com.zzycreate.zzz.user.rbac.mapstruct.RoleMapstruct;
import com.zzycreate.zzz.user.rbac.po.Perm;
import com.zzycreate.zzz.user.rbac.po.Role;
import com.zzycreate.zzz.user.rbac.po.RolePerm;
import com.zzycreate.zzz.user.rbac.po.UserRole;
import com.zzycreate.zzz.user.rbac.service.RbacService;
import com.zzycreate.zzz.utils.LambdaHelper;
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
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhenyao.zhao
 * @date 2019/11/25
 */
@Service
public class RbacServiceImpl implements RbacService {

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermMapper rolePermMapper;
    @Resource
    private PermMapper permMapper;

    @Resource
    private IdApi idApi;

    // ---------------- permission ----------------

    @Override
    public PermBO addPerm(PermCreateBO permCreate) {
        Perm perm = PermMapstruct.INSTANCE.createToPo(permCreate);
        Long permId = Optional.of(this.idApi.getId(IdEnums.PERM_ID.getKey())).map(String::trim).map(Long::parseLong)
                .orElseThrow(() -> {
                    throw new ServiceException("无法获取权限ID！");
                });
        perm.setPermId(permId);
        this.permMapper.insert(perm);
        return this.getPerm(permId);
    }

    @Override
    public PermBO updatePerm(Long permId, PermUpdateBO permUpdate) {
        if (permId == null) {
            throw new ServiceException("权限ID不能为空");
        }
        if (StringUtils.isBlank(permUpdate.getPermName()) && StringUtils.isBlank(permUpdate.getPermCode())
                && StringUtils.isBlank(permUpdate.getPermDescription())) {
            return this.getPerm(permId);
        }

        LambdaUpdateWrapper<Perm> wrapper = Wrappers.<Perm>lambdaUpdate()
                .set(StringUtils.isNotBlank(permUpdate.getPermName()), Perm::getPermName, permUpdate.getPermName())
                .set(StringUtils.isNotBlank(permUpdate.getPermCode()), Perm::getPermCode, permUpdate.getPermCode())
                .set(StringUtils.isNotBlank(permUpdate.getPermDescription()),
                        Perm::getPermDescription, permUpdate.getPermDescription())
                .eq(Perm::getPermId, permId);
        this.permMapper.update(null, wrapper);

        return this.getPerm(permId);
    }

    @Override
    public void deletePerm(Long permId) {
        if (permId != null) {
            this.permMapper.delete(Wrappers.<Perm>lambdaQuery().eq(Perm::getPermId, permId));
            this.rolePermMapper.delete(Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getPermId, permId));
        }
    }

    @Override
    public PermBO getPerm(Long permId) {
        Perm perm = this.permMapper.selectOne(Wrappers.<Perm>lambdaQuery().eq(Perm::getPermId, permId));
        return perm == null ? null : PermMapstruct.INSTANCE.to(perm);
    }

    @Override
    public PermBO getPerm(String permName, String permCode) {
        if (StringUtils.isBlank(permName) && StringUtils.isBlank(permCode)) {
            return null;
        }
        LambdaQueryWrapper<Perm> wrapper = Wrappers.<Perm>lambdaQuery()
                .eq(StringUtils.isNotBlank(permName), Perm::getPermName, permName)
                .eq(StringUtils.isNotBlank(permCode), Perm::getPermCode, permCode);
        Perm perm = this.permMapper.selectOne(wrapper);
        return perm == null ? null : PermMapstruct.INSTANCE.to(perm);
    }

    @Override
    public List<PermBO> getPerms(List<Long> permIds) {
        if (CollectionUtils.isEmpty(permIds)) {
            return new ArrayList<>();
        }
        List<Perm> perms = this.permMapper.selectList(Wrappers.<Perm>lambdaQuery().in(Perm::getPermId, permIds));
        return CollectionUtils.isEmpty(perms) ? new ArrayList<>() : PermMapstruct.INSTANCE.to(perms);
    }

    @Override
    public List<PermBO> getAllPerms() {
        List<Perm> perms = this.permMapper.selectList(Wrappers.lambdaQuery());
        return CollectionUtils.isEmpty(perms) ? new ArrayList<>() : PermMapstruct.INSTANCE.to(perms);
    }

    @Override
    public Map<Long, PermBO> getPermMap(List<Long> permIds) {
        List<PermBO> perms = this.getPerms(permIds);
        return perms.stream().collect(Collectors.toMap(PermBO::getPermId, Function.identity(), (a, b) -> a));
    }

    // ---------------- role_permission ----------------

    @Override
    public void bindPerm(Long roleId, List<Long> permIdList) {
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        if (CollectionUtils.isEmpty(permIdList)) {
            throw new ServiceException("权限ID不能为空");
        }
        // 移除 perm 表中不存在的 permId
        List<Long> permIdInP = this.permMapper.selectList(Wrappers.<Perm>lambdaQuery().in(Perm::getPermId, permIdList))
                .stream().map(Perm::getPermId).collect(Collectors.toList());
        List<Long> filter1 = permIdList.stream().filter(permIdInP::contains).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filter1)) {
            return;
        }

        // 移除 role_perm 表中存在的 permId
        List<Long> permIdInRp = this.rolePermMapper.selectList(Wrappers.<RolePerm>lambdaQuery()
                .eq(RolePerm::getRoleId, roleId).in(RolePerm::getPermId, filter1))
                .stream().map(RolePerm::getPermId).collect(Collectors.toList());
        List<Long> filter2 = filter1.stream().filter(id -> !permIdInRp.contains(id)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filter2)) {
            return;
        }

        // 绑定角色权限

        List<RolePerm> rolePermList = filter2.stream().map(permId -> RolePerm.builder()
                .rolePermId(Optional.of(this.idApi.getId(IdEnums.ROLE_PERM_ID.getKey()))
                        .map(String::trim).map(Long::parseLong)
                        .orElseThrow(() -> {
                            throw new ServiceException("无法获取角色权限ID！");
                        }))
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
        this.rolePermMapper.delete(
                Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, roleId).in(RolePerm::getPermId, permIdList));
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
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        if (CollectionUtils.isEmpty(permIdList)) {
            throw new ServiceException("权限ID不能为空");
        }
        this.emptyPerm(roleId);
        this.bindPerm(roleId, permIdList);
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
        List<RolePerm> rolePerms =
                this.rolePermMapper.selectList(Wrappers.<RolePerm>lambdaQuery().in(RolePerm::getRoleId, roleIdList));
        Map<Long, List<Long>> rolePermMap = rolePerms.stream().collect(Collectors.groupingBy(
                RolePerm::getRoleId, Collectors.mapping(RolePerm::getPermId, Collectors.toList())));

        List<Long> permIds = rolePerms.stream().map(RolePerm::getPermId).collect(Collectors.toList());
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
    public boolean hasPermForRole(Long roleId, Long permId) {
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        if (permId == null) {
            throw new ServiceException("权限ID不能为空");
        }
        Integer integer = this.rolePermMapper.selectCount(Wrappers.<RolePerm>lambdaQuery()
                .eq(RolePerm::getRoleId, roleId).eq(RolePerm::getPermId, permId));
        return integer != null && integer > 0;
    }

    @Override
    public boolean hasPermForRole(Long roleId, String permName, String permCode) {
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        PermBO perm = this.getPerm(permName, permCode);
        if (perm == null || perm.getPermId() == null) {
            return false;
        }
        return this.hasPermForRole(roleId, perm.getPermId());
    }

    // ---------------- role ----------------

    @Override
    public RoleBO addRole(RoleCreateBO roleCreate) {
        Role role = RoleMapstruct.INSTANCE.createToPo(roleCreate);
        Long roleId = Optional.of(this.idApi.getId(IdEnums.ROLE_ID.getKey())).map(String::trim).map(Long::parseLong)
                .orElseThrow(() -> {
                    throw new ServiceException("无法获取角色ID！");
                });
        role.setRoleId(roleId);
        this.roleMapper.insert(role);
        return this.getRole(roleId);
    }

    @Override
    public RoleBO updateRole(Long roleId, RoleUpdateBO roleUpdate) {
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        if (StringUtils.isBlank(roleUpdate.getRoleName()) && StringUtils.isBlank(roleUpdate.getRoleCode())
                && StringUtils.isBlank(roleUpdate.getRoleDescription())) {
            return this.getRole(roleId);
        }

        LambdaUpdateWrapper<Role> wrapper = Wrappers.<Role>lambdaUpdate()
                .set(StringUtils.isNotBlank(roleUpdate.getRoleName()), Role::getRoleName, roleUpdate.getRoleName())
                .set(StringUtils.isNotBlank(roleUpdate.getRoleCode()), Role::getRoleCode, roleUpdate.getRoleCode())
                .set(StringUtils.isNotBlank(roleUpdate.getRoleDescription()),
                        Role::getRoleDescription, roleUpdate.getRoleDescription())
                .eq(Role::getRoleId, roleId);
        this.roleMapper.update(null, wrapper);

        return this.getRole(roleId);
    }

    @Override
    public void deleteRole(Long roleId) {
        if (roleId != null) {
            this.userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId, roleId));
            this.roleMapper.delete(Wrappers.<Role>lambdaQuery().eq(Role::getRoleId, roleId));
            this.rolePermMapper.delete(Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, roleId));
        }
    }

    @Override
    public RoleBO getRole(Long roleId) {
        Role role = this.roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleId, roleId));
        if (role == null) {
            return null;
        }

        RoleBO bo = RoleMapstruct.INSTANCE.to(role);

        // 查询 RolePerm
        List<RolePerm> rolePerms = this.rolePermMapper.selectList(
                Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, role.getRoleId()));
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
    public RoleBO getRole(String roleName, String roleCode) {
        if (StringUtils.isBlank(roleName) && StringUtils.isBlank(roleCode)) {
            return null;
        }
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .eq(StringUtils.isNotBlank(roleName), Role::getRoleName, roleName)
                .eq(StringUtils.isNotBlank(roleCode), Role::getRoleCode, roleCode);
        Role role = this.roleMapper.selectOne(wrapper);
        return role == null ? null : RoleMapstruct.INSTANCE.to(role);
    }

    @Override
    public List<RoleBO> getRoles(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        List<Role> roles = this.roleMapper.selectList(Wrappers.<Role>lambdaQuery().in(Role::getRoleId, roleIds));
        List<RoleBO> roleBos = RoleMapstruct.INSTANCE.to(roles);

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

    // ---------------- user_role ----------------

    @Override
    public void bindRole(Long userId, List<Long> roleIdList) {
        if (userId == null) {
            throw new ServiceException("用户ID不能为空");
        }
        if (CollectionUtils.isEmpty(roleIdList)) {
            throw new ServiceException("角色ID不能为空");
        }
        // 移除 role 表中不存在的 roleId
        List<Long> roleIdInR = this.roleMapper.selectList(Wrappers.<Role>lambdaQuery().in(Role::getRoleId, roleIdList))
                .stream().map(Role::getRoleId).collect(Collectors.toList());
        List<Long> filter1 = roleIdList.stream().filter(roleIdInR::contains).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filter1)) {
            return;
        }

        // 移除 user_role 表中存在的 roleId
        List<Long> roleIdInUr = this.userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId).in(UserRole::getRoleId, filter1))
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Long> filter2 = filter1.stream().filter(id -> !roleIdInUr.contains(id)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filter2)) {
            return;
        }

        // 绑定用户角色
        List<UserRole> userRoleList = filter2.stream().map(roleId -> UserRole.builder()
                .userRoleId(Optional.of(this.idApi.getId(IdEnums.USER_ROLE_ID.getKey()))
                        .map(String::trim).map(Long::parseLong)
                        .orElseThrow(() -> {
                            throw new ServiceException("无法获取角色ID！");
                        }))
                .userId(userId).roleId(roleId).build()).collect(Collectors.toList());
        userRoleList.forEach(ur -> this.userRoleMapper.insert(ur));

    }

    @Override
    public void unBindRole(Long userId, List<Long> roleIdList) {
        if (userId == null) {
            throw new ServiceException("用户ID不能为空");
        }
        if (CollectionUtils.isEmpty(roleIdList)) {
            throw new ServiceException("角色ID不能为空");
        }
        this.userRoleMapper.delete(
                Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId).in(UserRole::getRoleId, roleIdList));
    }

    @Override
    public void emptyRole(Long userId) {
        if (userId != null) {
            this.userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId));
        }
    }

    @Override
    public void reBindRole(Long userId, List<Long> roleIdList) {
        if (userId == null) {
            throw new ServiceException("用户ID不能为空");
        }
        if (CollectionUtils.isEmpty(roleIdList)) {
            throw new ServiceException("角色ID不能为空");
        }
        this.emptyRole(userId);
        this.bindRole(userId, roleIdList);
    }

    @Override
    public List<RoleBO> getRolesByUserIds(Long userId) {
        Map<Long, List<RoleBO>> roleMapByUserIds = this.getRoleMapByUserIds(Collections.singletonList(userId));
        return roleMapByUserIds.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public Map<Long, List<RoleBO>> getRoleMapByUserIds(List<Long> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return new HashMap<>(16);
        }

        List<UserRole> userRoles = this.userRoleMapper.selectList(
                Wrappers.<UserRole>lambdaQuery().in(UserRole::getUserId, userIdList));
        Map<Long, List<Long>> userRoleMap = LambdaHelper.groupMapList(userRoles,
                UserRole::getUserId, UserRole::getRoleId);
        List<Long> roleIds =
                userRoles.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList());

        Map<Long, RoleBO> roleMap = this.getRoleMap(roleIds);
        Map<Long, List<RoleBO>> result = new HashMap<>(16);
        userRoleMap.forEach((userId, roleIdList) -> {
            List<RoleBO> tmpRoles =
                    roleIdList.stream().map(roleMap::get).filter(Objects::nonNull).collect(Collectors.toList());
            result.put(userId, tmpRoles);
        });

        return result;
    }

    @Override
    public boolean hasRoleForUser(Long userId, Long roleId) {
        if (userId == null) {
            throw new ServiceException("用户ID不能为空");
        }
        if (roleId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        Integer integer = this.userRoleMapper.selectCount(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, roleId));
        return integer != null && integer > 0;
    }

    @Override
    public boolean hasRoleForUser(Long userId, String roleName, String roleCode) {
        if (userId == null) {
            throw new ServiceException("用户ID不能为空");
        }
        RoleBO role = this.getRole(roleName, roleCode);
        if (role == null || role.getRoleId() == null) {
            return false;
        }
        return this.hasRoleForUser(userId, role.getRoleId());
    }

    @Override
    public boolean hasPermForUser(Long userId, Long permId) {
        if (userId == null) {
            throw new ServiceException("用户ID不能为空");
        }
        if (permId == null) {
            throw new ServiceException("角色ID不能为空");
        }
        Integer integer = this.permMapper.countPermForUser(userId, permId);
        return integer != null && integer > 0;
    }

    @Override
    public boolean hasPermForUser(Long userId, String permName, String permCode) {
        if (userId == null) {
            throw new ServiceException("用户ID不能为空");
        }
        PermBO perm = this.getPerm(permName, permCode);
        if (perm == null) {
            return false;
        }
        return this.hasPermForUser(userId, perm.getPermId());
    }

}
