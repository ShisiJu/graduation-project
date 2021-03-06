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
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Institute;
import com.jss.app.repository.GroupRepository;
import com.jss.app.repository.InstituteRepository;

@Service
@Transactional(readOnly = true)
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private InstituteRepository instituteRepository;

	public List<Group> findByGroupNameIn(List<String> listName) {

		return groupRepository.findByNameIn(listName);
	}

	public List<Group> findByInstitute_id(Long id) {

		return groupRepository.findByInstitute_id(id);
	}

	public List<Group> findByInstitute_idIn(List<Long> listId) {

		return groupRepository.findByInstitute_idIn(listId);
	}

	public List<Group> findAllGroups() {

		return groupRepository.findAll();
	}

	private Specification<Group> specification(JSONObject jsonObject) {

		String name = jsonObject.getString("name");
		List<Long> instituteIds = jsonObject.getJSONArray("instituteIds").toJavaList(Long.class);

		boolean boolIns = !instituteIds.isEmpty();

		Specification<Group> specification = Specifications.<Group>and()
				.like(!StringUtils.isEmpty(name), "name", "%" + name + "%")
				.in(boolIns, "institute.id", instituteIds.toArray()).build();

		return specification;

	}

	public Page<Group> searchGroups(JSONObject jsonObject) {

		Specification<Group> specification = specification(jsonObject);

		Integer index = jsonObject.getInteger("index");
		Integer pageSize = jsonObject.getInteger("pageSize");

		Sort sort = new Sort(Sort.Direction.ASC, "code");
		Pageable pageable = PageRequest.of(index - 1, pageSize, sort);

		return groupRepository.findAll(specification, pageable);
	}

	public List<Group> searchGroupsWithoutPage(JSONObject jsonObject) {

		Specification<Group> specification = specification(jsonObject);
		Sort sort = new Sort(Sort.Direction.ASC, "code");
		return groupRepository.findAll(specification, sort);
	}

	@Transactional
	public void deleteGroupById(Long id) {

		groupRepository.deleteById(id);
	}

	@Transactional
	public Group saveGroup(Group group, Long instituteId) {

		if (instituteId != null) {
			Optional<Institute> institute = instituteRepository.findById(instituteId);
			group.setInstitute(institute.get());
		}

		return groupRepository.save(group);
	}

}
