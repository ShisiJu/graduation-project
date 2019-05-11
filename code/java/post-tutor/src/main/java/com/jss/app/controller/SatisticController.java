package com.jss.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jss.app.service.SatisticService;

@RestController
@RequestMapping("/statistic")
public class SatisticController {

	@Autowired
	private SatisticService satisticService;

	@RequestMapping("/tutor")
	public List<Map<String, Object>> evaluateCourseByTutorId(Long tutorId) {
		
		return satisticService.satisticRecent5yearByTutorId(tutorId);
	}

}
