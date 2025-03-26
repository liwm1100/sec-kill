/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liwm.sk.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liwm.sk.user.mapper.UserAccountMapper;
import com.liwm.sk.user.model.UserAccount;
import com.liwm.sk.user.service.AccountService;
import com.liwm.sk.common.dto.BusinessException;
import com.liwm.sk.common.dto.Result;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author TrevorLink
 */
@Service
public class AccountServiceImpl implements AccountService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserAccountMapper userAccountMapper;

	@Override
	@Transactional
	public void reduceBalance(Long userId, BigDecimal money) {
		logger.info("[reduceBalance] currenet XID: {}", RootContext.getXID());

		checkBalance(userId, money);

		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		int updateCount = userAccountMapper.reduceBalance(userId, money, updateTime);
		if (updateCount == 0) {
			throw new BusinessException("扣钱失败！");
		}
	}

	private void checkBalance(Long userId, BigDecimal price) throws BusinessException {
		UserAccount account = userAccountMapper.selectOne(Wrappers.lambdaQuery(UserAccount.class).eq(UserAccount::getUserId,userId));
		if (account.getMoney().compareTo(price) < 0) {
			throw new BusinessException("金额不足");
		}
	}

}
