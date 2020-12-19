package com.jale.weblog.shopping.api.dataobject;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("商品")
@Data
public class Goods implements Serializable {

    @ApiModelProperty(value = "商品id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "库存数量", example = "1")
    private Integer stockCount;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "添加事件")
    private Date addTime;

    @ApiModelProperty(value = "更新事件")
    private Date updateTime;

    @ApiModelProperty(value = "是否已删除", example = "1")
    @TableLogic
    private Integer isDelete;



}
