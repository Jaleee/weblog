package com.jale.weblog.shopping.api.dataobject;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShoppingCar implements Serializable {

    private Integer id;

    private Long userId;

    private Integer goodsId;

    private String goodsName;

    private Integer stockCount;

    private Date addTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;

}
