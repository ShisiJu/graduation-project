package com.jss.app.service;

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
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.github.wenhao.jpa.Specifications;
import com.jss.app.model.entity.User;
import com.jss.app.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Page<User> searchUser(@RequestBody JSONObject jsonObject) {

		Sort sort = new Sort(Sort.Direction.ASC, "type", "studno");

		Optional<Integer> index = Optional.ofNullable(jsonObject.getInteger("index"));
		Optional<Integer> pageSize = Optional.ofNullable(jsonObject.getInteger("pageSize"));
		

		Pageable pageable = PageRequest.of(index.orElse(1) - 1, pageSize.orElse(10), sort);

		Integer type = jsonObject.getInteger("type");
		String studno = jsonObject.getString("studno");

		Specification<User> specification = Specifications.<User>and().eq(type != null, "type", type)
				.like(!StringUtils.isEmpty(studno), "studno", "%" + studno + "%")
				.build();

		return userRepository.findAll(specification, pageable);

	}

	@Transactional
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Transactional
	public Boolean updatePwd(Long userId, String pwd, String newPwd) {

		Optional<User> findById = userRepository.findById(userId);

		String oldPwd = findById.get().getPwd();

		if (oldPwd.equals(pwd) == false) {
			return false;
		}

		userRepository.updatePwd(userId, newPwd);
		return true;

	}

	@Transactional
	public void resetPwd(Long userId) {

		Optional<User> findById = userRepository.findById(userId);

		String studno = findById.get().getStudno();

		userRepository.updatePwd(userId, studno);

	}

}
