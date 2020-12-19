package com.jale.weblog.user.api.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserWallet {

    @TableId(type = IdType.INPUT)
    private Long userId;

    private BigDecimal balance;

}
