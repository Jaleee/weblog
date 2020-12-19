package com.jale.weblog.shopping.api.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Orderform implements Serializable {

    @TableId(type = IdType.INPUT)
    private Integer orderNumber;

    private Long userId;

    private Integer goodsId;

    private String goodsName;

    private Integer stockCount;

    private BigDecimal singlePrice;

    private BigDecimal totalPrice;

    private Date addTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;

}
