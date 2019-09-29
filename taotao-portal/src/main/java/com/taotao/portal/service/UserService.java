package com.taotao.portal.service;

import com.taotao.pojo.User;

public interface UserService {

	User getUserByToken(String token);

}
