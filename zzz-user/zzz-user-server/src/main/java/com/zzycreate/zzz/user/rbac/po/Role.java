package com.zzycreate.zzz.user.rbac.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zzycreate.zf.mybatis.Base;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * RBAC: 角色表
 *
 * @author zhenyao.zhao
 * @date 2019/11/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "t_rbac_role")
public class Role extends Base {

    /**
     * 角色ID（业务编号）
     */
    @TableField(value = "role_id")
    private Long roleId;
    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;
    /**
     * 角色编号
     */
    @TableField(value = "role_code")
    private String roleCode;
    /**
     * 角色描述
     */
    @TableField(value = "role_description")
    private String roleDescription;
    /**
     * 是否默认角色
     */
    @TableField(value = "default_role")
    private Integer defaultRole;
    /**
     * 角色所属客户(系统角色SYSTEM)
     */
    @TableField(value = "cust_no")
    private String custNo;

}
