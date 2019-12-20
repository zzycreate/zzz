package com.zzycreate.zzz.user.rbac.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
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
public class RoleCreateBO implements Serializable {
    private static final long serialVersionUID = -5890790299389253747L;
    /**
     * 角色名称
     */
    @NotEmpty(message = "新建角色的角色名不能为空！")
    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;
    /**
     * 角色编号
     */
    @NotEmpty(message = "新建角色的角色编号不能为空！")
    @ApiModelProperty(value = "角色编号", required = true)
    private String roleCode;
    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String roleDescription;

}
