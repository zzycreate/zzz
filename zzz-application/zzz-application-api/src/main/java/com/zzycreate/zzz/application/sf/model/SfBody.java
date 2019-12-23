package com.zzycreate.zzz.application.sf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SfBody {

    private String xml;
    private String verifyCode;

}
