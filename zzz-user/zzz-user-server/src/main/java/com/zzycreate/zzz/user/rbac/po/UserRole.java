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
@TableName(value = "t_rbac_user_role")
public class UserRole extends Base {

    private static final long serialVersionUID = 2937571840736667347L;
    /**
     * 角色权限关联ID（关联ID）
     */
    @TableField(value = "user_role_id")
    private Long userRoleId;
    /**
     * 角色ID
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 权限ID
     */
    @TableField(value = "role_id")
    private Long roleId;

}
