package com.zzycreate.zzz.user.rbac.mapstruct;

import com.zzycreate.zf.core.support.BaseMapping;
import com.zzycreate.zzz.user.rbac.bo.PermBO;
import com.zzycreate.zzz.user.rbac.po.Perm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zhenyao.zhao
 * @date 2019/11/26
 */
@Mapper
public interface PermMapping extends BaseMapping<Perm, PermBO> {

    PermMapping INSTANCE = Mappers.getMapper(PermMapping.class);

}
