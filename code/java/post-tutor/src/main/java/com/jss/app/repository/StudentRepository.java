package com.jss.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Student;

public interface StudentRepository extends RowBaseRepository<Student, Long> {
	Student findByStudno(String studno);

	List<Student> findByGroup(Group group);

	Page<Student> findByStudnoContainingAndGroup_id(@Nullable String studno,@Nullable Long id, Pageable pageable);

}
