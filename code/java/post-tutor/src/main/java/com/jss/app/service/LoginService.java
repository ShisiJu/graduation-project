package com.jss.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jss.app.model.entity.User;
import com.jss.app.repository.AdminRepository;
import com.jss.app.repository.StudentRepository;
import com.jss.app.repository.TutorRepository;
import com.jss.app.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private AdminRepository adminRepository;
	
	public User findUser(Integer type, String username, String pwd) {
		
		User user = userRepository.findByTypeAndUsernameAndPwd(type, username, pwd);
		
		return user;
	}
	
	
	public Object findObject(Integer type,String studno) {
		if(type == 0) 
			return studentRepository.findByStudno(studno);
		
		if(type == 1)
			return tutorRepository.findByStudno(studno);
		
		if(type == 2)
			return adminRepository.findByStudno(studno);
		
		return null;
	}
	

}
