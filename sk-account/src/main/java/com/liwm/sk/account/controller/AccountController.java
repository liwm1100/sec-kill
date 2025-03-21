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

package com.liwm.sk.account.controller;

import com.liwm.sk.account.dto.AccountDTO;
import com.liwm.sk.account.service.AccountService;
import com.liwm.sk.common.dto.BusinessException;
import com.liwm.sk.common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author TrevorLink
 */
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping("/reduce-balance")
	public Result<?> reduceBalance(@RequestBody AccountDTO accountDTO) {
		try {
			accountService.reduceBalance(accountDTO.getUserId(), accountDTO.getPrice());
		}
		catch (BusinessException e) {
			return Result.fail(e.getMessage());
		}
		return Result.success("");
	}

	@GetMapping("/")
	public Result<?> getRemainAccount(String userId) {
		return accountService.getRemainAccount(userId);
	}

}
