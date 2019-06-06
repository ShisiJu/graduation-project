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

import com.alibaba.fastjson.JSONObject;
import com.github.wenhao.jpa.Specifications;
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Tutor;
import com.jss.app.model.entity.User;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.TutorRepository;
import com.jss.app.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class TutorService {

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private InstituteRepository instituteRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Tutor> findAll() {

		return tutorRepository.findAll();
	}

	public Tutor findByStudno(String studno) {

		return tutorRepository.findByStudno(studno);
	}

	@Transactional
	public void deleteTutor(Long tutorId) {
		Optional<Tutor> tutor = tutorRepository.findById(tutorId);
		userRepository.deleteByStudno(tutor.get().getStudno());
		tutorRepository.deleteById(tutorId);
	}

	private Specification<Tutor> specification(JSONObject jsonObject) {

		String studno = jsonObject.getString("studno");
		List<Long> instituteIds = jsonObject.getJSONArray("instituteIds").toJavaList(Long.class);

		boolean boolIns = !instituteIds.isEmpty();

		Specification<Tutor> specification = Specifications.<Tutor>and()
				.like(!StringUtils.isEmpty(studno), "studno", "%" + studno + "%")
				.in(boolIns, "institute.id", instituteIds.toArray()).build();

		return specification;
	}

	public Page<Tutor> searchTutors(JSONObject jsonObject) {

		Integer index = jsonObject.getInteger("index");
		Integer pageSize = jsonObject.getInteger("pageSize");

		Sort sort = new Sort(Sort.Direction.ASC, "studno");
		Pageable pageable = PageRequest.of(index - 1, pageSize, sort);

		Specification<Tutor> specification = specification(jsonObject);

		return tutorRepository.findAll(specification, pageable);
	}

	public List<Tutor> searchTutorsWithoutPage(JSONObject jsonObject) {

		Sort sort = new Sort(Sort.Direction.ASC, "studno");

		Specification<Tutor> specification = specification(jsonObject);

		return tutorRepository.findAll(specification, sort);
	}

	@Transactional
	public Tutor saveTutor(Tutor tutor, Long instituteId) {

		Optional<Institute> institute = instituteRepository.findById(instituteId);
		if (!institute.isPresent()) {
			throw new RuntimeException("institute_id: " + instituteId + " 不存在");
		}

		if (institute.get().getId() == null) {
			String studno = tutor.getStudno();
			User user = User.builder().studno(studno).username(studno).pwd(studno).type(1).build();
			userRepository.save(user);
		}

		tutor.setInstitute(institute.get());
		return tutorRepository.save(tutor);

	}

}
