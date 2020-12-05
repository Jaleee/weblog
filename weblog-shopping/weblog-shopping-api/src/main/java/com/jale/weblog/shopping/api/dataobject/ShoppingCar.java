package com.jale.weblog.shopping.api.dataobject;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class ShoppingCar {

    private Integer id;

    private String goodsId;

    private Integer goodsName;

    private Integer stockCount;

    private Date addTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;

}
