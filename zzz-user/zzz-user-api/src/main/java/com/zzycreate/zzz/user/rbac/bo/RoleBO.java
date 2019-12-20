package com.zzycreate.zzz.user.rbac.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 角色信息
 *
 * @author zhenyao.zhao
 * @date 2019/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class RoleBO implements Serializable {
    private static final long serialVersionUID = -5890790299389253747L;
    /**
     * 角色ID（业务编号）
     */
    @ApiModelProperty(name = "角色ID（业务编号）")
    private Long roleId;
    /**
     * 角色名称
     */
    @ApiModelProperty(name = "角色名称")
    private String roleName;
    /**
     * 角色编号
     */
    @ApiModelProperty(name = "角色编号")
    private String roleCode;
    /**
     * 角色描述
     */
    @ApiModelProperty(name = "角色描述")
    private String roleDescription;
    /**
     * 是否默认角色
     */
    @ApiModelProperty(name = "是否默认角色")
    private Integer defaultRole;
    /**
     * 角色所属客户(系统角色SYSTEM)
     */
    @ApiModelProperty(name = "角色所属客户")
    private String custNo;

    @ApiModelProperty(name = "权限")
    private List<PermBO> perms;

    @ApiModelProperty(name = "权限ID")
    private List<Long> permIds;

    @ApiModelProperty(name = "权限编号")
    private List<String> permCodes;

}
