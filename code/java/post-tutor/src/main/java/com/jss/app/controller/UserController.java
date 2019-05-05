package com.jss.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.entity.User;
import com.jss.app.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/search")
	public Page<User> searchUser(@RequestBody JSONObject jsonObject) {

		Page<User> users = userService.searchUser(jsonObject);
		users.forEach(user -> {
			user.setPwd(null);
		});

		return users;

	}
	//resetPwd
	
	@RequestMapping("/resetPwd")
	public void resetPwd(Long userId) {
		userService.resetPwd(userId);
	}
	
	@RequestMapping("/updatePwd")
	public Boolean updatePwd(Long userId, String pwd, String newPwd) {
		return userService.updatePwd(userId, pwd, newPwd);
	}
	

}
