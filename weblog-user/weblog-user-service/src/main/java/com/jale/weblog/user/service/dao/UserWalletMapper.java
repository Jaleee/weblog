package com.jale.weblog.user.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jale.weblog.user.api.dataobject.UserWallet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserWalletMapper extends BaseMapper<UserWallet> {
}
