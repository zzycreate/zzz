package com.zzycreate.zzz.user.rbac.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限信息
 *
 * @author zhenyao.zhao
 * @date 2019/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PermBO implements Serializable {
    private static final long serialVersionUID = 5076961532341804184L;
    /**
     * 权限ID（业务编号）
     */
    @ApiModelProperty(name = "权限ID（业务编号）")
    private Long permId;
    /**
     * 权限名称
     */
    @ApiModelProperty(name = "权限名称")
    private String permName;
    /**
     * 权限编号
     */
    @ApiModelProperty(name = "权限编号")
    private String permCode;
    /**
     * 权限描述
     */
    @ApiModelProperty(name = "权限描述")
    private String permDescription;
}
