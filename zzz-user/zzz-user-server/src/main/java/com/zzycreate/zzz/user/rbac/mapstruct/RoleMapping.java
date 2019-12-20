package com.zzycreate.zzz.user.rbac.mapstruct;

import com.zzycreate.zf.core.support.BaseMapping;
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
public interface RoleMapping extends BaseMapping<Role, RoleBO> {

    RoleMapping INSTANCE = Mappers.getMapper(RoleMapping.class);

    /**
     * RoleCreateBO -> RoleDO
     *
     * @param create RoleCreateBO
     * @return RoleDO
     */
    @Mappings({
            @Mapping(target = "roleId", ignore = true)
    })
    Role createToDo(RoleCreateBO create);

    /**
     * RoleCreateBO -> RoleBO
     *
     * @param create RoleCreateBO
     * @return RoleBO
     */
    @Mappings({
            @Mapping(target = "roleId", ignore = true),
            @Mapping(target = "perms", ignore = true),
            @Mapping(target = "permIds", ignore = true),
            @Mapping(target = "permCodes", ignore = true)
    })
    RoleBO createToBo(RoleCreateBO create);


}
