package com.zzycreate.zzz.core.enums;

import lombok.Getter;

/**
 * @author zzycreate
 * @date 2019/8/12
 */
@Getter
public enum ResultEnum {

    /**
     * 成功结果
     */
    SUCCESS(0, "SUCCESS"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
