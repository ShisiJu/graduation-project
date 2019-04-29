package com.jss.app.repository;

import java.util.List;

import com.jss.app.model.entity.Group;

public interface GroupRepository extends RowBaseRepository<Group, Long> {

	List<Group> findByInstitute_id(Long id);

	List<Group> findByInstitute_idIn(List<Long> id);

}
