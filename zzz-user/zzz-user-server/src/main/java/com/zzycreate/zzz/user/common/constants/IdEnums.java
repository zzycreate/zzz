package com.zzycreate.zzz.user.common.constants;

import lombok.Getter;

/**
 * @author zzycreate
 * @date 2019/12/20
 */
@Getter
public enum IdEnums {

    /**
     * id
     */
    USER_ID("zzz-user_use-id"),
    USER_ROLE_ID("zzz-user_user-role-id"),
    ROLE_ID("zzz-user_role-id"),
    ROLE_PERM_ID("zzz-user_role-perm-id"),
    PERM_ID("zzz-user_perm-id"),
    ;

    private String key;

    IdEnums(String key) {
        this.key = key;
    }

}
