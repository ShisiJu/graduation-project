package com.jss.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jss.app.model.entity.Group;
import com.jss.app.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	public List<Group> findByInstitute_id(Long id) {
		
		return groupRepository.findByInstitute_id(id);
	}
	
	public List<Group> findByInstitute_idIn(List<Long> listId) {
		
		return groupRepository.findByInstitute_idIn(listId);
	}


	public List<Group> findAllGroups() {
		
		return groupRepository.findAll();
	}

}
