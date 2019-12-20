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
 * RBAC: 权限表
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
@TableName(value = "t_rbac_perm")
public class Perm extends Base {

    private static final long serialVersionUID = 5455199071680168787L;
    /**
     * 权限ID（业务编号）
     */
    @TableField(value = "perm_id")
    private Long permId;
    /**
     * 权限名称
     */
    @TableField(value = "perm_name")
    private String permName;
    /**
     * 权限编号
     */
    @TableField(value = "perm_code")
    private String permCode;
    /**
     * 权限描述
     */
    @TableField(value = "perm_description")
    private String permDescription;

}
