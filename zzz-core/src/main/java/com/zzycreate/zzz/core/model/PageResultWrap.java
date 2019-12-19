package com.zzycreate.zzz.core.model;

import org.apache.commons.collections4.CollectionUtils;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 页面结果包装
 * 用于 PageResult 对象转换
 *
 * @author zzycreate
 * @date 2019/9/11
 */
public class PageResultWrap<S, T> {

    private PageResult<S> source;

    public PageResultWrap(PageResult<S> source) {
        this.source = source;
    }

    public PageResult<S> getSource() {
        return source;
    }

    public void setSource(PageResult<S> source) {
        this.source = source;
    }

    public PageResult<T> wrap(Function<S, T> function) {
        PageResult<T> result = new PageResult<>();
        if (this.source == null || CollectionUtils.isEmpty(this.source.getData())) {
            return result;
        }
        result.setPageNo(this.source.getPageNo())
            .setPageSize(this.source.getPageSize())
            .setTotalPage(this.source.getTotalPage())
            .setTotalRecords(this.source.getTotalRecords())
            .setData(this.source.getData().stream().map(function).collect(Collectors.toList()));
        return result;
    }

}
