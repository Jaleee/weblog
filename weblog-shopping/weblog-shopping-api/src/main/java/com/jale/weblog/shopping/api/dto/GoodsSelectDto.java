package com.jale.weblog.shopping.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("商品查询条件")
@Data
public class GoodsSelectDto {

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "库存数量", example = "1")
    private Integer stockCount;

    @ApiModelProperty(value = "当前页", example = "1")
    private Integer currentPage;

    @ApiModelProperty(value = "页大小", example = "1")
    private Integer pageSize;

}
