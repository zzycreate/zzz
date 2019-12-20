package com.zzycreate.zzz.user.rbac.mapstruct;

import com.zzycreate.zf.core.support.BaseMapstruct;
import com.zzycreate.zzz.user.rbac.bo.RoleBO;
import com.zzycreate.zzz.user.rbac.bo.RoleCreateBO;
import com.zzycreate.zzz.user.rbac.po.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author zhenyao.zhao
 * @date 2019/11/26
 */
@Mapper
public interface RoleMapstruct extends BaseMapstruct<Role, RoleBO> {

    RoleMapstruct INSTANCE = Mappers.getMapper(RoleMapstruct.class);

    /**
     * RoleCreateBO -> RoleDO
     *
     * @param create RoleCreateBO
     * @return RoleDO
     */
    @Mappings({
            @Mapping(target = "roleId", ignore = true)
    })
    Role createToPo(RoleCreateBO create);

}
