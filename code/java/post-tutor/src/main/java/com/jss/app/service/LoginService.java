package com.jss.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jss.app.model.entity.User;
import com.jss.app.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	public User findUser(Integer type, String username, String pwd) {
		
		User user = userRepository.findByTypeAndUsernameAndPwd(type, username, pwd);
		
		return user;
	}

}
