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
 * RBAC: 角色权限关联表
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
@TableName(value = "t_rbac_role_perm")
public class RolePerm extends Base {

    /**
     * 角色权限关联ID（关联ID）
     */
    @TableField(value = "role_perm_id")
    private Long rolePermId;
    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;
    /**
     * 权限ID
     */
    @TableField(value = "perm_id")
    private Long permId;

}
