package com.jale.weblog.user.api.service;

import com.jale.weblog.user.api.dataobject.UserWallet;

public interface UserWalletService {

    UserWallet getByUserId(Long userId);

    int update(UserWallet userWallet);

}
