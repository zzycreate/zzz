package com.zzycreate.zzz.core.model;

import com.zzycreate.zzz.core.enums.SortEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 可分页可排序参数
 *
 * @author zzycreate
 * @date 2019/8/12
 */
@ApiModel("可分页可排序参数")
public class PageSortable extends Pageable {

    private static final long serialVersionUID = 5901464100866009442L;

    @ApiModelProperty(value = "排序字段", required = true, example = "id")
    private String field;
    /**
     * @see SortEnum
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
        return "PageSortable{" +
            "field='" + field + '\'' +
            ", order='" + order + '\'' +
            "} " + super.toString();
    }
}
