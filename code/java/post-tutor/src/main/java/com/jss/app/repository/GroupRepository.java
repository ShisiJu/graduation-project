package com.jss.app.repository;

import java.util.List;

import com.jss.app.model.entity.Group;

public interface GroupRepository extends RowBaseRepository<Group, Long> {

	List<Group> findByInstitute_id(Long id);

	List<Group> findByInstitute_idIn(List<Long> id);

	Group findByName(String name);
	
	List<Group> findByNameIn(List<String> listName);
	
	List<Group> findByIdIn(List<Long> listId);
	

}
