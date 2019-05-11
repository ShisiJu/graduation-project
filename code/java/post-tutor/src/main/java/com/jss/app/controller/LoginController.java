package com.jss.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jss.app.model.entity.User;
import com.jss.app.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@RequestMapping("/login")
	public ResponseEntity<Object> login(@RequestParam Integer type, @RequestParam String username, String pwd) {

		User user = loginService.findUser(type, username, pwd);

		if (user == null)
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

		Object obj = loginService.findObject(type, user.getStudno());

		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("user", user);
		userInfo.put("obj", obj);

		return new ResponseEntity<Object>(userInfo, HttpStatus.OK);
	}

}
