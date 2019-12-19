package com.zzycreate.zzz.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 可排序参数
 *
 * @author zzycreate
 * @date 2019/8/12
 */
@ApiModel("可排序参数")
public class Sortable implements Serializable {

    private static final long serialVersionUID = 4067682976783217772L;

    @ApiModelProperty(value = "排序字段", required = true, example = "id")
    private String field;
    /**
     * @see zzycreate.zcloud.core.enums.SortEnum
     */
    @ApiModelProperty(value = "排序方式，asc-升序，desc-降序", required = true, example = "asc")
    private String order;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Sortable{" +
            "field='" + field + '\'' +
            ", order='" + order + '\'' +
            '}';
    }
}
