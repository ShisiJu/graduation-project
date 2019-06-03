package com.jss.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.repository.CourseRepository;
import com.jss.app.repository.StudentCourseRepository;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class PostTutorApplication implements ApplicationRunner {

	@Autowired
	private CourseRepository cr;
	
	@Autowired
	private StudentCourseRepository scr;

	public static void main(String[] args) {
		SpringApplication.run(PostTutorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		initData();

	}

	@Transactional
	private void initData() {
		
		List<StudentCourse> findByCourse_Tutor_Studno = scr.findByCourse_Tutor_Studno("11000");
		System.out.println("-----");
	}

}
