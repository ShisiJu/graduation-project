package com.jss.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jss.app.model.entity.Group;
import com.jss.app.repository.GroupRepository;

@Service
public class GroupService {
	
	private GroupRepository groupRepository;
	
	public List<Group> findAllGroups(){
		return groupRepository.findAll();
	}

}
