package com.jss.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.wenhao.jpa.Specifications;
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Tutor;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.TutorRepository;

@Service
@Transactional
public class TutorService {

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private InstituteRepository instituteRepository;

	public void deleteTutor(Long tutorId) {

		tutorRepository.deleteById(tutorId);
	}

	@Transactional(readOnly = true)
	public Page<Tutor> searchTutors(String studno, List<Long> instituteIds, Integer index, Integer pageSize) {

		Sort sort = new Sort(Sort.Direction.ASC, "studno");
		Pageable pageable = PageRequest.of(index - 1, pageSize, sort);

		boolean boolIns = !instituteIds.isEmpty();

		Specification<Tutor> specification = Specifications.<Tutor>and()
				.like(!StringUtils.isEmpty(studno), "studno", "%" + studno + "%")
				.in(boolIns, "institute.id", instituteIds.toArray()).build();

		return tutorRepository.findAll(specification, pageable);
	}

	public Tutor saveTutor(Tutor tutor, Long instituteId) {

		Optional<Institute> institute = instituteRepository.findById(instituteId);
		if (!institute.isPresent()) {
			throw new RuntimeException("institute_id: " + instituteId + " 不存在");
		}

		tutor.setInstitute(institute.get());
		return tutorRepository.save(tutor);

	}

}
