package com.jss.app.repository;

import com.jss.app.model.entity.Tutor;

public interface TutorRepository extends RowBaseRepository<Tutor, Long> {
	Tutor findByStudno(String studno);
}
