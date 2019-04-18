package com.jss.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jss.app.model.entity.School;
import com.jss.app.repository.SchoolRepository;

@Service
public class SchoolService {
	@Autowired
	private SchoolRepository schoolRepository;
	
	public List<School> findAll(){
		List<School> listSchool = schoolRepository.findAll();
		return listSchool;
	}
	
}
