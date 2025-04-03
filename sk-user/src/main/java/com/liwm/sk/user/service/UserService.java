package com.liwm.sk.user.service;

import com.liwm.sk.user.dto.UserDTO;


/**
 * @author TrevorLink
 */
public interface UserService {

    String login(UserDTO userDTO);

    boolean register(UserDTO userDTO);

    UserDTO getCurrentUserInfo();

}
