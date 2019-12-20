package com.zzycreate.zzz.user.rbac.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zzycreate.zzz.user.rbac.po.Perm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zhenyao.zhao
 * @date 2019/11/25
 */
@Mapper
public interface PermMapper extends BaseMapper<Perm> {

    @Select("SELECT count(1) FROM t_rbac_user_role ur LEFT JOIN t_rbac_role r ON ur.role_id = r.role_id " +
            "LEFT JOIN t_rbac_role_perm rp ON r.role_id = rp.role_id " +
            "LEFT JOIN t_rbac_perm p ON rp.perm_id = p.perm_id " +
            "WHERE ur.user_id = #{userId} AND p.permId = #{permId} ")
    Integer countPermForUser(@Param("userId") Long userId, @Param("permId") Long permId);

    @Select("SELECT count(1) FROM t_rbac_user_role ur LEFT JOIN t_rbac_role r ON ur.role_id = r.role_id " +
            "LEFT JOIN t_rbac_role_perm rp ON r.role_id = rp.role_id " +
            "LEFT JOIN t_rbac_perm p ON rp.perm_id = p.perm_id " +
            "WHERE ur.user_id = #{userId} AND p.perm_name = #{permName} ")
    Integer countPermForUserByName(@Param(Constants.WRAPPER) Wrapper wrapper);

}
