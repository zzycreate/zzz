package com.zzycreate.zzz.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据库类型
 *
 * @author zzycreate
 * @date 2019/8/11
 */
public class BasePO implements Serializable {

    private static final long serialVersionUID = 7558546480507939314L;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BasePO{" +
            "createTime=" + createTime +
            ", updateTime=" + updateTime +
            '}';
    }
}
