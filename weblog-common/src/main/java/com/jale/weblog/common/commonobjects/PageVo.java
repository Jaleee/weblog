package com.jale.weblog.common.commonobjects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageVo<T> {

    private List<T> data;

    private Long currentPage;

    private Long pageSize;

    private Long totalPage;

    private Long totalData;

}
