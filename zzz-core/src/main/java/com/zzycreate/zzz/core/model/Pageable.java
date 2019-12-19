package com.zzycreate.zzz.core.model;

import com.zzycreate.zzz.core.constants.PageConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 可分页参数
 *
 * @author zzycreate
 * @date 2019/8/12
 */
@ApiModel("可分页参数")
public class Pageable implements Serializable {

    private static final long serialVersionUID = -2022952590574385963L;

    @ApiModelProperty(value = "页码，从 1 开始", required = true, example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条数，最大值为 100", required = true, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Range(min = PageConstants.PAGE_SIZE_MIN, max = PageConstants.PAGE_SIZE_MAX,
        message = "条数范围为 [" + PageConstants.PAGE_SIZE_MIN + ", " + PageConstants.PAGE_SIZE_MAX + "]")
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public Pageable setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Pageable setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public String toString() {
        return "Pageable{" +
            "pageNo=" + pageNo +
            ", pageSize=" + pageSize +
            '}';
    }
}
