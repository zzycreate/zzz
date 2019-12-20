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
 * 权限新增信息
 *
 * @author zhenyao.zhao
 * @date 2019/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PermCreateBO implements Serializable {
    private static final long serialVersionUID = 6387051888448764903L;
    /**
     * 权限名称
     */
    @NotEmpty(message = "新建权限的权限名不能为空！")
    @ApiModelProperty(value = "权限名称", required = true)
    private String permName;
    /**
     * 权限编号
     */
    @NotEmpty(message = "新建权限的权限编号不能为空！")
    @ApiModelProperty(value = "权限编号", required = true)
    private String permCode;
    /**
     * 权限描述
     */
    @ApiModelProperty("权限描述")
    private String permDescription;

}
