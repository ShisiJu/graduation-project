package com.jss.app.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Student;

public interface StudentRepository extends RowBaseRepository<Student, Long> {
	Student findByStudno(String studno);

	List<Student> findByGroup(Group group);

	Page<Student> findByStudnoContainingAndGroup_id(@Nullable String studno, @Nullable Long id, Pageable pageable);

	@Query(value = "SELECT id FROM t_student stud WHERE stud.group_id in (?1) ", nativeQuery = true)
	List<BigInteger> findStudentsIdsByGroupsIds(List<Long> groupsIds);

}
