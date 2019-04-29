package com.jss.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jss.app.model.entity.Institute;
import com.jss.app.repository.InstituteRepository;

@Service
public class InstituteService {

	@Autowired
	private InstituteRepository instituteRepository;

	public List<Institute> findAllInstitute() {
		return instituteRepository.findAll();
	}

}
