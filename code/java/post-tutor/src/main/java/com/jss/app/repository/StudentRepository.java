package com.jss.app.repository;


import java.util.List;

import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Student;

public interface StudentRepository extends RowBaseRepository<Student, Long>{
	Student findByStudno(Long studno);
	List<Student> findByGroup(Group group);
}
