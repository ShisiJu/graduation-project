package com.jss.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.entity.Tutor;
import com.jss.app.service.TutorService;

@RequestMapping("/tutor")
@RestController
public class TutorController {

	@Autowired
	private TutorService tutorService;

	@RequestMapping("/deleteTutor")
	public void deleteTutor(Long tutorId) {

		tutorService.deleteTutor(tutorId);
	}

	@RequestMapping("/saveTutor")
	public Tutor saveTutor(@RequestBody JSONObject jsonObject) {

		Tutor tutor = jsonObject.getObject("tutor", Tutor.class);
		Long instituteId = jsonObject.getLong("instituteId");
		return tutorService.saveTutor(tutor, instituteId);

	}

	@RequestMapping("/all")
	public List<Tutor> findAll() {

		return tutorService.findAll();
	}

	@RequestMapping("/searchTutors")
	public Page<Tutor> searchTutors(@RequestBody JSONObject jsonObject) {

		String studno = jsonObject.getString("studno");
		List<Long> instituteIds = jsonObject.getJSONArray("instituteIds").toJavaList(Long.class);
		Integer index = jsonObject.getInteger("index");
		Integer pageSize = jsonObject.getInteger("pageSize");
		return tutorService.searchTutors(studno, instituteIds, index, pageSize);
	}

}
