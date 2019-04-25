package com.jss.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Quiz;
import com.jss.app.model.entity.QuizAnswer;

@Transactional
public interface QuizAnswerRepository extends RowBaseRepository<QuizAnswer, Long> {

	@Modifying
	@Query(value = "insert QuizAnswer qa set qa.quiz = ?1 where u.lastname = ?2", nativeQuery = true)
	int setFixedFirstnameFor(String firstname, String lastname);
	
	List<QuizAnswer> findByQuizIn(List<Quiz> listQuiz);

}
