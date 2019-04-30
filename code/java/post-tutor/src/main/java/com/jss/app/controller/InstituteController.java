package com.jss.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
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

	@RequestMapping("/page")
	public Page<Institute> findAllInstituteWithPage(Integer index, Integer pageSize) {

		return instituteService.findAllInstituteWithPage(index, pageSize);
	}

	@RequestMapping("/save")
	public Institute saveInstitute(@RequestBody JSONObject jsonObject) {
		// 学校只有一个,所以不用传school_id
		Institute institute = jsonObject.toJavaObject(Institute.class);
		return instituteService.saveInstitute(institute);
	}

	@RequestMapping("/delete")
	public void deleteInstituteById(Long instituteId) {
		// 学校只有一个,所以不用传school_id
		instituteService.deleleInstituteById(instituteId);
	}

}
