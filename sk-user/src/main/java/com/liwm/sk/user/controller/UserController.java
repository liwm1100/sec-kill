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

package com.liwm.sk.user.controller;

import com.liwm.sk.common.dto.Result;
import com.liwm.sk.user.dto.AccountDTO;
import com.liwm.sk.user.dto.UserDTO;
import com.liwm.sk.user.service.AccountService;
import com.liwm.sk.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TrevorLink
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public Result<String> login(@RequestBody UserDTO userDTO) {
		try {
			return Result.success(userService.login(userDTO));
		} catch (Exception e) {
			return Result.fail(e.getMessage());
		}
	}

	@PostMapping("/register")
	public Result<Boolean> register(@RequestBody UserDTO userDTO) {
		try {
			return Result.success(userService.register(userDTO));
		} catch (Exception e) {
			return Result.fail(e.getMessage());
		}
	}
	@PostMapping("/info")
	public Result<UserDTO> userInfo() {
		try {
			return Result.success(userService.getCurrentUserInfo());
		} catch (Exception e) {
			return Result.fail(e.getMessage());
		}
	}

}
