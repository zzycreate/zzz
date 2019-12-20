package com.zzycreate.zzz.user.rbac.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色新增信息
 *
 * @author zhenyao.zhao
 * @date 2019/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class RoleUpdateBO implements Serializable {
    private static final long serialVersionUID = -6061521702525222352L;
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;
    /**
     * 角色编号
     */
    @ApiModelProperty("角色编号")
    private String roleCode;
    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String roleDescription;

}
