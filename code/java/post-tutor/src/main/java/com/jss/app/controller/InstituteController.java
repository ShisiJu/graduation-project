package com.jss.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jss.app.model.entity.Institute;
import com.jss.app.service.InstituteService;

@RequestMapping("/institute")
@RestController
public class InstituteController {

	@Autowired
	private InstituteService instituteService;

	@RequestMapping("/all")
	public List<Institute> findAllInstitute() {

		return instituteService.findAllInstitute();
	}

}
