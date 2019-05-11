package com.jss.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.jss.app.model.entity.School;
import com.jss.app.service.SchoolService;

@RestController
@RequestMapping("/school")
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
	
	@RequestMapping("/all")
	public ResponseEntity<List<School>> school() {
		
		List<School> findAll = schoolService.findAll();
		return new ResponseEntity<List<School>>(findAll,HttpStatus.CREATED);
	}

}
