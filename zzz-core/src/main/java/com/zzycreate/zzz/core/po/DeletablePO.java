package com.zzycreate.zzz.core.po;

import com.zzycreate.zzz.core.enums.DeletedStatusEnum;

/**
 * extends BaseDO 扩展 delete 操作
 *
 * @author zzycreate
 * @date 2019/8/11
 */
public class DeletablePO extends BasePO {

    private static final long serialVersionUID = -6055227504098290947L;
    /**
     * 是否删除
     *
     * @see DeletedStatusEnum#getCode()
     */
    private Integer deleted;

    public Integer getDeleted() {
        return deleted;
    }

    public DeletablePO setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "DeletablePO{" +
            "deleted=" + deleted +
            "} " + super.toString();
    }
}
