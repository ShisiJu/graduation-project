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

	@RequestMapping("/recent")
	public List<Map<String, Object>> satisticRecent5yearByTutorId(Long tutorId) {

		return satisticService.satisticRecent5yearByTutorId(tutorId);
	}

	@RequestMapping("/tutor")
	public List<Map<String, Object>> satisticByTutor(Boolean asc) {

		if (asc == null) {
			asc = true;
		}

		return satisticService.satisticByTutor(asc);
	}

	@RequestMapping("/tutor-question")
	public List<Map<String, Object>> satisticByTutorAndQuestion(Boolean asc, Integer question) {
		
		if (asc == null) {
			asc = true;
		}
		List<Map<String, Object>> satisticByTutorAndQuestion = satisticService.satisticByTutorAndQuestion(asc, question);
		
		return satisticByTutorAndQuestion;
	}

	@RequestMapping("/institute")
	 
	public List<Map<String, Object>> satisticByInstitute(Boolean asc) {
		if (asc == null) {
			asc = true;
		}
		return satisticService.satisticByInstitute(asc);
	}

}
