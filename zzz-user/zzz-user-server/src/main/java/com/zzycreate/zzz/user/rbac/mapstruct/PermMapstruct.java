package com.zzycreate.zzz.user.rbac.mapstruct;

import com.zzycreate.zf.core.support.BaseMapstruct;
import com.zzycreate.zzz.user.rbac.bo.PermBO;
import com.zzycreate.zzz.user.rbac.bo.PermCreateBO;
import com.zzycreate.zzz.user.rbac.po.Perm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author zhenyao.zhao
 * @date 2019/11/26
 */
@Mapper
public interface PermMapstruct extends BaseMapstruct<Perm, PermBO> {

    PermMapstruct INSTANCE = Mappers.getMapper(PermMapstruct.class);

    /**
     * PermCreateBO -> Perm
     *
     * @param create PermCreateBO
     * @return Perm
     */
    @Mappings({
            @Mapping(target = "permId", ignore = true)
    })
    Perm createToPo(PermCreateBO create);

}
