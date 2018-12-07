package com.ssm.config.predefined;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类
 *
 * @author FaceFeel
 * @Created  2018-05-04 10:11
 */
public class Page<T> implements Serializable {

    private long totalRow;
    private int pageCurrent;
    private int pageSize;
    private List<T> list;


    public static Page defaultPage(){
        return new Page();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("totalRow", totalRow)
                .add("pageCurrent", pageCurrent)
                .add("pageSize", pageSize)
                .add("list", list)
                .toString();
    }

    public long getTotalRow() {
        return totalRow;
    }

    public Page<T> setTotalRow(long totalRow) {
        this.totalRow = totalRow;
        return this;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public Page<T> setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Page<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public Page<T> setList(List<T> list) {
        this.list = list;
        return this;
    }
}
