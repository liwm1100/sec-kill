package com.liwm.sk.user.service;

import com.liwm.sk.common.dto.Result;

import java.math.BigDecimal;

/**
 * @author TrevorLink
 */
public interface AccountService {

	void reduceBalance(Long userId, BigDecimal price);

}
