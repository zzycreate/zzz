package com.zzycreate.zzz.user.rbac.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzycreate.zf.core.exception.ServiceException;
import com.zzycreate.zzz.user.BaseTransactionalNgTest;
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
import com.zzycreate.zzz.user.rbac.po.Perm;
import com.zzycreate.zzz.user.rbac.po.Role;
import com.zzycreate.zzz.user.rbac.po.RolePerm;
import com.zzycreate.zzz.user.rbac.po.UserRole;
import com.zzycreate.zzz.utils.CastHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author zzycreate
 * @date 2019/12/21
 */
@Slf4j
public class RbacServiceTest extends BaseTransactionalNgTest {

    private static final long DEFAULT_ROLE_ID = 0L;
    private static final long DEFAULT_USER_ID = 0L;
    private static final String TEST_ROLE_NAME = "系统管理员test1";
    private static final Long[] DEFAULT_PERM_IDS = new Long[6];

    @Resource
    private RbacService rbacService;

    @Resource
    private UserRoleMapper UserRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermMapper rolePermMapper;
    @Resource
    private PermMapper permMapper;

    private List<PermBO> buildDefaultPerm() {
//        INSERT INTO `t_rbac_perm` (gmt_create, gmt_modified, perm_id, perm_name, perm_code, perm_description) VALUES (NOW(), NOW(), 1, '加入购物车权限', 'cart:add', '加入购物车权限');
//        INSERT INTO `t_rbac_perm` (gmt_create, gmt_modified, perm_id, perm_name, perm_code, perm_description) VALUES (NOW(), NOW(), 2, '购物车导入权限', 'cart:import', '导入购物车权限');
//        INSERT INTO `t_rbac_perm` (gmt_create, gmt_modified, perm_id, perm_name, perm_code, perm_description) VALUES (NOW(), NOW(), 3, '购物车导出权限', 'cart:export', '导出购物车权限');
//        INSERT INTO `t_rbac_perm` (gmt_create, gmt_modified, perm_id, perm_name, perm_code, perm_description) VALUES (NOW(), NOW(), 11, '查看商品价格权限', 'price:view', '查看商品价格权限');
//        INSERT INTO `t_rbac_perm` (gmt_create, gmt_modified, perm_id, perm_name, perm_code, perm_description) VALUES (NOW(), NOW(), 21, '询价单提交权限', 'quote:create', '询价单提交权限');
//        INSERT INTO `t_rbac_perm` (gmt_create, gmt_modified, perm_id, perm_name, perm_code, perm_description) VALUES (NOW(), NOW(), 22, '询价单管理权限', 'quote:manage', '询价单管理权限');
        PermBO perm1 = this.rbacService.addPerm(PermCreateBO.builder().permName("加入购物车权限").permCode("cart:add").build());
        PermBO perm2 = this.rbacService.addPerm(PermCreateBO.builder().permName("购物车导入权限").permCode("cart:import").build());
        PermBO perm3 = this.rbacService.addPerm(PermCreateBO.builder().permName("购物车导出权限").permCode("cart:export").build());
        PermBO perm4 = this.rbacService.addPerm(PermCreateBO.builder().permName("查看商品价格权限").permCode("price:view").build());
        PermBO perm5 = this.rbacService.addPerm(PermCreateBO.builder().permName("询价单提交权限").permCode("quote:create").build());
        PermBO perm6 = this.rbacService.addPerm(PermCreateBO.builder().permName("询价单管理权限").permCode("quote:manage").build());
        List<PermBO> list = new ArrayList<>(Arrays.asList(perm1, perm2, perm3, perm4, perm5, perm6));
        for (int i = 0; i < list.size(); i++) {
            DEFAULT_PERM_IDS[i] = list.get(i).getPermId();
        }

        return list;
    }

    private List<Role> buildDefaultRole() {

        RoleCreateBO role1 = RoleCreateBO.builder().roleName("角色test1").roleCode("roleTest1").build();
        RoleCreateBO role2 = RoleCreateBO.builder().roleName("角色test2").roleCode("roleTest2").build();
        RoleCreateBO role3 = RoleCreateBO.builder().roleName("角色test3").roleCode("roleTest3").build();
        RoleCreateBO role4 = RoleCreateBO.builder().roleName("角色test4").roleCode("roleTest4").build();
        this.rbacService.addRole(role1);
        this.rbacService.addRole(role2);
        this.rbacService.addRole(role3);
        this.rbacService.addRole(role4);

        return this.selectDefaultRole();
    }

    private List<Perm> selectDefaultPerm() {
        return this.permMapper.selectList(Wrappers.<Perm>lambdaQuery().in(Perm::getPermId, DEFAULT_PERM_IDS));
    }

    private List<Role> selectDefaultRole() {
        return this.roleMapper.selectList(Wrappers.<Role>lambdaQuery().in(Role::getRoleName,
                new ArrayList<>(Arrays.asList("角色test1", "角色test2", "角色test3", "角色test4"))));
    }

    @Test
    public void addPerm() {
        PermBO perm1 = this.rbacService.addPerm(PermCreateBO.builder().permName("测试权限1").permCode("permTest1").build());
        assertNotNull(perm1);
        assertNotNull(perm1.getPermId());
    }

    @Test
    public void updatePerm() {
        this.addPerm();
        PermBO perm = this.rbacService.getPerm("测试权限1", null);
        assertNotNull(perm);
        assertNotNull(perm.getPermId());

        this.rbacService.updatePerm(perm.getPermId(), PermUpdateBO.builder().permCode("tttest").build());

        PermBO perm1 = this.rbacService.getPerm("测试权限1", null);
        assertNotNull(perm1);
        assertNotNull(perm1.getPermId());
        assertEquals("tttest", perm1.getPermCode());
    }

    @Test
    public void deletePerm() {
        this.addPerm();
        PermBO perm1 = this.rbacService.getPerm("测试权限1", null);
        assertNotNull(perm1);
        assertNotNull(perm1.getPermId());

        this.rbacService.deletePerm(perm1.getPermId());

        PermBO perm2 = this.rbacService.getPerm("测试权限1", null);
        assertNull(perm2);

    }

    @Test
    public void getPerm() {
        List<PermBO> PermS = this.buildDefaultPerm();
        List<Long> permIds = PermS.stream().map(PermBO::getPermId).collect(Collectors.toList());

        permIds.forEach(permId -> {
            PermBO perm = this.rbacService.getPerm(permId);
            log.info("getPerm.perm: {}", perm);
            assertNotNull(perm);
            assertEquals(permId, perm.getPermId());
        });
    }

    @Test
    public void getPerm2() {
        this.addPerm();
        PermBO perm1 = this.rbacService.getPerm("测试权限1", null);
        assertNotNull(perm1);
        assertNotNull(perm1.getPermId());
    }

    @Test
    public void getPerms() {
        List<PermBO> PermS = this.buildDefaultPerm();
        List<Long> permIds = PermS.stream().map(PermBO::getPermId).collect(Collectors.toList());

        List<PermBO> perms = this.rbacService.getPerms(permIds);
        log.info("getPerms.perms: {}", perms);

        perms.forEach(perm -> {
            assertNotNull(perm);
            assertTrue(permIds.contains(perm.getPermId()));
        });
    }

    @Test
    public void getAllPerms() {
        this.buildDefaultPerm();

        List<PermBO> perms = this.rbacService.getAllPerms();
        log.info("getPerms.perms: {}", perms);

        perms.forEach(Assert::assertNotNull);
    }

    @Test
    public void getPermMap() {
        List<PermBO> PermS = this.buildDefaultPerm();
        List<Long> permIds = PermS.stream().map(PermBO::getPermId).collect(Collectors.toList());

        Map<Long, PermBO> permMap = this.rbacService.getPermMap(permIds);
        log.info("getPermMap.permMap: {}", permMap);
        assertTrue(MapUtils.isNotEmpty(permMap));

        permMap.forEach((permId, perm) -> {
            assertNotNull(perm);
            assertTrue(permIds.contains(perm.getPermId()));
            assertEquals(permId, perm.getPermId());
        });
    }

    @Test
    public void bindPerm() {
        List<PermBO> PermS = this.buildDefaultPerm();
        log.info("bindPerm.PermS: {}", PermS);
        List<Long> permIds = PermS.stream().map(PermBO::getPermId).collect(Collectors.toList());
        log.info("bindPerm.permIds: {}", permIds);
        assertNotNull(permIds);
        assertTrue(CollectionUtils.isNotEmpty(permIds));
        this.rbacService.bindPerm(DEFAULT_ROLE_ID, permIds);

        List<RolePerm> rolePermS = this.rolePermMapper.selectList(
                Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, DEFAULT_ROLE_ID));
        log.info("bindPerm.rolePermS: {}", rolePermS);
        assertNotNull(rolePermS);
        assertTrue(CollectionUtils.isNotEmpty(rolePermS));
        assertEquals(PermS.size(), rolePermS.size());
        rolePermS.forEach(rp -> assertTrue(permIds.contains(rp.getPermId())));

    }

    @Test(expectedExceptions = ServiceException.class)
    public void bindPermException() {
        List<PermBO> PermS = this.buildDefaultPerm();
        List<Long> permIds = PermS.stream().map(PermBO::getPermId).collect(Collectors.toList());
        assertNotNull(permIds);
        assertTrue(CollectionUtils.isNotEmpty(permIds));
        this.rbacService.bindPerm(null, permIds);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void bindPermException1() {
        this.rbacService.bindPerm(DEFAULT_ROLE_ID, new ArrayList<>());
    }

    @Test(dependsOnMethods = "bindPerm")
    public void unBindPerm() {
        this.bindPerm();

        List<Perm> PermS = this.selectDefaultPerm();
        List<Long> permIds = PermS.stream().map(Perm::getPermId).collect(Collectors.toList());
        log.info("unBindPerm.permIds: {}", permIds);
        assertNotNull(permIds);
        assertTrue(CollectionUtils.isNotEmpty(permIds));
        this.rbacService.unBindPerm(DEFAULT_ROLE_ID, permIds);

        List<RolePerm> rolePermS = this.rolePermMapper.selectList(
                Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, DEFAULT_ROLE_ID));
        log.info("unBindPerm.rolePermS: {}", rolePermS);
        assertNotNull(rolePermS);
        assertTrue(CollectionUtils.isEmpty(rolePermS));
    }

    @Test(dependsOnMethods = "bindPerm")
    public void emptyPerm() {
        this.bindPerm();

        this.rbacService.emptyPerm(DEFAULT_ROLE_ID);

        List<RolePerm> rolePermS = this.rolePermMapper.selectList(
                Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, DEFAULT_ROLE_ID));
        log.info("emptyPerm.rolePermS: {}", rolePermS);
        assertNotNull(rolePermS);
        assertTrue(CollectionUtils.isEmpty(rolePermS));
    }

    @Test(dependsOnMethods = "bindPerm")
    public void reBindPerm() {
        this.bindPerm();

        List<Long> permIds = CastHelper.toList(DEFAULT_PERM_IDS).subList(0, 3);
        this.rbacService.reBindPerm(DEFAULT_ROLE_ID, permIds);

        List<RolePerm> rolePermS = this.rolePermMapper.selectList(
                Wrappers.<RolePerm>lambdaQuery().eq(RolePerm::getRoleId, DEFAULT_ROLE_ID));
        log.info("reBindPerm.rolePermS: {}", rolePermS);
        assertNotNull(rolePermS);
        assertTrue(CollectionUtils.isNotEmpty(rolePermS));
        assertEquals(permIds.size(), rolePermS.size());
        rolePermS.forEach(rp -> assertTrue(permIds.contains(rp.getPermId())));
    }

    @Test
    public void getPermsByRoleId() {
        this.bindPerm();
        List<Perm> PermS = this.selectDefaultPerm();
        List<Long> permIds = PermS.stream().map(Perm::getPermId).collect(Collectors.toList());

        List<PermBO> perms = this.rbacService.getPermsByRoleId(DEFAULT_ROLE_ID);
        log.info("getPermMapByRoleIds.perms: {}", perms);
        assertTrue(CollectionUtils.isNotEmpty(perms));
        assertEquals(permIds.size(), perms.size());
    }

    @Test
    public void getPermMapByRoleIds() {
        this.bindPerm();
        List<Perm> PermS = this.selectDefaultPerm();
        List<Long> permIds = PermS.stream().map(Perm::getPermId).collect(Collectors.toList());

        Map<Long, List<PermBO>> permMap = this.rbacService.getPermMapByRoleIds(Collections.singletonList(DEFAULT_ROLE_ID));
        log.info("getPermMapByRoleIds.permMap: {}", permMap);
        assertTrue(MapUtils.isNotEmpty(permMap));
        assertEquals(1, permMap.size());

        permMap.forEach((bindId, permList) -> {
            assertNotNull(bindId);
            assertEquals(DEFAULT_USER_ID, bindId.longValue());
            assertNotNull(permList);
            assertTrue(CollectionUtils.isNotEmpty(permList));
            permList.forEach(perm -> assertTrue(permIds.contains(perm.getPermId())));
        });
    }

    @Test(dependsOnMethods = "bindPerm")
    public void hasPermForRole() {
        this.bindPerm();
        Long permId = DEFAULT_PERM_IDS[0];
        assertTrue(this.rbacService.hasPermForRole(DEFAULT_ROLE_ID, permId));
    }

    @Test
    public void hasPermForRole2() {
        this.bindPerm();
        Long permId = DEFAULT_PERM_IDS[0];
        PermBO perm = this.rbacService.getPerm(permId);
        assertTrue(this.rbacService.hasPermForRole(DEFAULT_ROLE_ID, perm.getPermName(), null));
    }

    @Test
    public void addRole() {
        RoleCreateBO createBO = RoleCreateBO.builder().roleName(TEST_ROLE_NAME).roleCode("systemAdmin")
                .roleDescription("管理公司所有业务").build();
        this.rbacService.addRole(createBO);
        Role role = this.roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, TEST_ROLE_NAME));

        log.info("addRole.Role: {}", role);
        assertNotNull(role);
        assertEquals(TEST_ROLE_NAME, role.getRoleName());
        assertEquals("systemAdmin", role.getRoleCode());
        assertEquals("管理公司所有业务", role.getRoleDescription());
    }

    @Test(dependsOnMethods = "addRole")
    public void updateRole() {
        this.addRole();

        Role roleAdd = this.roleMapper.selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleName, TEST_ROLE_NAME));

        RoleUpdateBO roleUpdate = RoleUpdateBO.builder().roleDescription("管理测试").build();
        this.rbacService.updateRole(roleAdd.getRoleId(), roleUpdate);

        Role role = this.roleMapper.selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleName, TEST_ROLE_NAME));

        log.info("updateRole.Role: {}", role);
        assertNotNull(role);
        assertEquals(TEST_ROLE_NAME, role.getRoleName());
        assertEquals("systemAdmin", role.getRoleCode());
        assertEquals("管理测试", role.getRoleDescription());
    }

    @Test(dependsOnMethods = "bindRole")
    public void deleteRole() {
        this.bindRole();

        List<Role> RoleS = this.selectDefaultRole();
        assertNotNull(RoleS);
        assertEquals(4, RoleS.size());

        Long roleId = RoleS.get(0).getRoleId();

        this.rbacService.deleteRole(roleId);

        List<Role> RoleS1 = this.selectDefaultRole();
        assertNotNull(RoleS1);
        assertEquals(3, RoleS1.size());
    }

    @Test
    public void getRole() {
        List<Role> RoleS = this.buildDefaultRole();
        List<Long> roleIds = RoleS.stream().map(Role::getRoleId).collect(Collectors.toList());

        roleIds.forEach(roleId -> {
            RoleBO role = this.rbacService.getRole(roleId);
            log.info("getRole.role: {}", role);
            assertNotNull(role);
            assertEquals(roleId, role.getRoleId());
        });

        Long roleId = roleIds.get(0);
        List<PermBO> PermS = this.buildDefaultPerm();
        List<Long> permIds = PermS.stream().map(PermBO::getPermId).collect(Collectors.toList());
        assertTrue(CollectionUtils.isNotEmpty(permIds));
        this.rbacService.bindPerm(roleId, permIds);
        RoleBO role = this.rbacService.getRole(roleId);
        log.info("role: {}", role);
        assertNotNull(role);
        assertNotNull(role.getPermIds());
        for (Long permId : role.getPermIds()) {
            assertTrue(permIds.contains(permId));
        }

        RoleBO role1 = this.rbacService.getRole(-999L);
        assertNull(role1);

    }

    @Test(dependsOnMethods = "addRole")
    public void getRole2() {
        this.addRole();
        RoleBO role = this.rbacService.getRole(TEST_ROLE_NAME, null);
        assertNotNull(role);
        assertEquals(TEST_ROLE_NAME, role.getRoleName());
    }

    @Test
    public void getRoles() {
        List<Role> RoleS = this.buildDefaultRole();
        List<Long> roleIds = RoleS.stream().map(Role::getRoleId).collect(Collectors.toList());

        List<RoleBO> roles = this.rbacService.getRoles(roleIds);
        log.info("getRoles.roles: {}", roles);

        roles.forEach(role -> {
            assertNotNull(role);
            assertTrue(roleIds.contains(role.getRoleId()));
        });
    }

    @Test
    public void getRoleMap() {
        List<Role> RoleS = this.buildDefaultRole();
        List<Long> roleIds = RoleS.stream().map(Role::getRoleId).collect(Collectors.toList());

        Map<Long, RoleBO> roleMap = this.rbacService.getRoleMap(roleIds);
        log.info("getRoleMap.roleMap: {}", roleMap);
        assertTrue(MapUtils.isNotEmpty(roleMap));

        roleMap.forEach((roleId, role) -> {
            assertNotNull(role);
            assertTrue(roleIds.contains(role.getRoleId()));
            assertEquals(roleId, role.getRoleId());
        });
    }

    @Test
    public void bindRole() {
        List<Role> roleBOS = this.buildDefaultRole();
        log.info("bindRole.roleBOS: {}", roleBOS);
        List<Long> roleIds = roleBOS.stream().map(Role::getRoleId).collect(Collectors.toList());
        log.info("bindRole.roleIds: {}", roleIds);
        assertNotNull(roleIds);
        assertTrue(CollectionUtils.isNotEmpty(roleIds));
        this.rbacService.bindRole(DEFAULT_USER_ID, roleIds);

        List<UserRole> UserRoleS = this.UserRoleMapper.selectList(
                Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, DEFAULT_USER_ID));
        log.info("bindRole.UserRoleS: {}", UserRoleS);
        assertNotNull(UserRoleS);
        assertTrue(CollectionUtils.isNotEmpty(UserRoleS));
        assertEquals(roleBOS.size(), UserRoleS.size());
        UserRoleS.forEach(br -> assertTrue(roleIds.contains(br.getRoleId())));
    }

    @Test(dependsOnMethods = "bindRole")
    public void unBindRole() {
        this.bindRole();

        List<Role> RoleS = this.selectDefaultRole();
        assertNotNull(RoleS);
        assertTrue(CollectionUtils.isNotEmpty(RoleS));
        Long removeRoleId = RoleS.get(0).getRoleId();
        this.rbacService.unBindRole(DEFAULT_USER_ID, Collections.singletonList(removeRoleId));

        List<UserRole> UserRoleS = this.UserRoleMapper.selectList(
                Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, DEFAULT_USER_ID));
        log.info("unBindRole.UserRoleS: {}", UserRoleS);
        assertNotNull(UserRoleS);
        assertTrue(CollectionUtils.isNotEmpty(UserRoleS));
        assertEquals(RoleS.size() - 1, UserRoleS.size());
    }

    @Test(dependsOnMethods = "bindRole")
    public void emptyRole() {
        this.bindRole();

        this.rbacService.emptyRole(DEFAULT_USER_ID);

        List<UserRole> UserRoleS = this.UserRoleMapper.selectList(
                Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, DEFAULT_USER_ID));
        log.info("unBindRole.UserRoleS: {}", UserRoleS);
        assertNotNull(UserRoleS);
        assertTrue(CollectionUtils.isEmpty(UserRoleS));
    }

    @Test(dependsOnMethods = "bindRole")
    public void reBindRole() {
        this.bindRole();

        List<Role> RoleS = this.selectDefaultRole();
        assertNotNull(RoleS);
        assertTrue(CollectionUtils.isNotEmpty(RoleS));
        Long roleId1 = RoleS.get(0).getRoleId();
        Long roleId2 = RoleS.get(1).getRoleId();
        this.rbacService.reBindRole(DEFAULT_USER_ID, Arrays.asList(roleId1, roleId2));

        List<UserRole> UserRoleS = this.UserRoleMapper.selectList(
                Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, DEFAULT_USER_ID));
        log.info("reBindRole.UserRoleS: {}", UserRoleS);
        assertNotNull(UserRoleS);
        assertTrue(CollectionUtils.isNotEmpty(UserRoleS));
        assertEquals(2, UserRoleS.size());

    }

    @Test(dependsOnMethods = "bindRole")
    public void getRolesByUserIds() {
        this.bindRole();
        List<Role> RoleS = this.selectDefaultRole();
        List<Long> roleIds = RoleS.stream().map(Role::getRoleId).collect(Collectors.toList());

        List<RoleBO> roleList = this.rbacService.getRolesByUserIds(DEFAULT_USER_ID);
        assertNotNull(roleList);
        assertTrue(CollectionUtils.isNotEmpty(roleList));
        roleList.forEach(role -> assertTrue(roleIds.contains(role.getRoleId())));
    }

    @Test(dependsOnMethods = "bindRole")
    public void getRoleMapByUserIds() {
        this.bindRole();
        List<Role> RoleS = this.selectDefaultRole();
        List<Long> roleIds = RoleS.stream().map(Role::getRoleId).collect(Collectors.toList());

        Map<Long, List<RoleBO>> roleMap = this.rbacService.getRoleMapByUserIds(Collections.singletonList(DEFAULT_USER_ID));
        log.info("getRoleMapByBindIds.roleMap: {}", roleMap);
        assertTrue(MapUtils.isNotEmpty(roleMap));
        assertEquals(1, roleMap.size());

        roleMap.forEach((bindId, roleList) -> {
            assertNotNull(bindId);
            assertEquals(DEFAULT_USER_ID, bindId.longValue());
            assertNotNull(roleList);
            assertTrue(CollectionUtils.isNotEmpty(roleList));
            roleList.forEach(role -> assertTrue(roleIds.contains(role.getRoleId())));
        });
    }

    @Test(dependsOnMethods = "bindRole")
    public void hasRoleForUser() {
        this.bindRole();
        List<Role> roles = this.selectDefaultRole();
        assertNotNull(roles);
        Long roleId = roles.get(0).getRoleId();
        assertNotNull(roleId);
        this.rbacService.hasRoleForUser(DEFAULT_USER_ID, roleId);
    }

    @Test(dependsOnMethods = "bindRole")
    public void hasRoleForUser2() {
        this.bindRole();
        List<Role> roles = this.selectDefaultRole();
        assertNotNull(roles);
        String roleName = roles.get(0).getRoleName();
        assertNotNull(roleName);
        assertTrue(this.rbacService.hasRoleForUser(DEFAULT_USER_ID, roleName, null));
    }

    @Test(dependsOnMethods = {"bindRole"})
    public void hasPermForUser() {
        this.bindRole();
        List<Role> roles = this.selectDefaultRole();
        assertNotNull(roles);
        Long roleId = roles.get(0).getRoleId();
        assertNotNull(roleId);

        this.buildDefaultPerm();
        this.rbacService.bindPerm(roleId, Arrays.asList(DEFAULT_PERM_IDS));

        Long permId = DEFAULT_PERM_IDS[0];
        assertNotNull(permId);
        assertTrue(this.rbacService.hasPermForUser(DEFAULT_USER_ID, permId));

    }

    @Test(dependsOnMethods = {"bindRole"})
    public void hasPermForUser2() {
        this.bindRole();
        List<Role> roles = this.selectDefaultRole();
        assertNotNull(roles);
        Long roleId = roles.get(0).getRoleId();
        assertNotNull(roleId);

        this.buildDefaultPerm();
        this.rbacService.bindPerm(roleId, Arrays.asList(DEFAULT_PERM_IDS));

        Long permId = DEFAULT_PERM_IDS[0];
        assertNotNull(permId);
        PermBO perm = this.rbacService.getPerm(permId);
        assertNotNull(perm);
        assertTrue(this.rbacService.hasPermForUser(DEFAULT_USER_ID, perm.getPermName(), null));
    }
}