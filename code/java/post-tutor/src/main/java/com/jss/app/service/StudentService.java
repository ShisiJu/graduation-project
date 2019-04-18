package com.jss.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.dictionary.Sex;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Student;
import com.jss.app.repository.GroupRepository;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;

	public void initStudent() throws Exception{
		
		
	}
	

	
	public void deleteLogicalById() {
		
	}
}
