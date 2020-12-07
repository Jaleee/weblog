package com.jale.weblog.common.commonobjects;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageVo {

    private List data;

    private Long currentPage;

    private Long pageSize;

    private Long totalPage;

    private Long totalData;

    public PageVo(IPage page) {
        this.data = page.getRecords();
        this.currentPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.totalPage = page.getPages();
        this.totalData = page.getTotal();
    }

}
