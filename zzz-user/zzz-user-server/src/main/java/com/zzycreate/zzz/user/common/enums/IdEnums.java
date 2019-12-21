package com.zzycreate.zzz.user.common.enums;

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
    USER_ID("zzz_user-user_id"),
    USER_ROLE_ID("zzz_user-user_role_id"),
    ROLE_ID("zzz_user-role_id"),
    ROLE_PERM_ID("zzz_user-role_perm_id"),
    PERM_ID("zzz_user-perm_id"),
    ;

    private String key;

    IdEnums(String key) {
        this.key = key;
    }

}
