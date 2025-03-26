package com.liwm.sk.account.service;

import com.liwm.sk.common.dto.Result;

/**
 * @author TrevorLink
 */
public interface AccountService {

	void reduceBalance(String userId, Integer price);

	Result<Integer> getRemainAccount(String userId);

}
