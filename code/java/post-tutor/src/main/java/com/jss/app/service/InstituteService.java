package com.jss.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.School;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.SchoolRepository;

@Service
@Transactional
public class InstituteService {

	@Autowired
	private InstituteRepository instituteRepository;
	
	@Autowired
	private SchoolRepository schoolRepository;
	
	@Transactional(readOnly = true)
	public List<Institute> findAllInstitute() {
		return instituteRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Page<Institute> findAllInstituteWithPage(Integer index, Integer pageSize) {
		Sort sort = new Sort(Sort.Direction.ASC, "code");
		Pageable pageable = PageRequest.of(index - 1, pageSize, sort);
		return instituteRepository.findAll(pageable);
	}

	public Institute saveInstitute(Institute institute) {
		School school = institute.getSchool();
		
		if(school ==null) {
			School school2 = schoolRepository.findById(1L).get();
			institute.setSchool(school2);
		}
		
		return instituteRepository.save(institute);
	}

	public void deleleInstituteById(Long id) {

		instituteRepository.deleteById(id);
	}

}
