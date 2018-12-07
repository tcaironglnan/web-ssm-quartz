package com.ssm.utils;

import com.github.pagehelper.PageInfo;
import com.ssm.config.predefined.Page;

/**
 * @author FaceFeel
 * @Created 2018-05-04 10:12
 **/
public class PageUtil {

    public static <T> Page<T> page(PageInfo pageInfo) {

        Page<T> page = new Page<>();
        page.setList(pageInfo.getList())
                .setPageCurrent(pageInfo.getPageNum())
                .setPageSize(pageInfo.getPageSize())
                .setTotalRow(pageInfo.getTotal());
        return page;
    }
}
