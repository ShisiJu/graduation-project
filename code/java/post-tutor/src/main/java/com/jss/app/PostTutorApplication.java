package com.jss.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.wenhao.jpa.Specifications;
import com.jss.app.model.dictionary.CommonDictionary;
import com.jss.app.model.dictionary.Sex;
import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Quiz;
import com.jss.app.model.entity.School;
import com.jss.app.model.entity.Student;
import com.jss.app.model.entity.Tutor;
import com.jss.app.model.entity.User;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.repository.CourseRepository;
import com.jss.app.repository.GroupRepository;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.QuizRepository;
import com.jss.app.repository.SchoolRepository;
import com.jss.app.repository.StudentCourseRepository;
import com.jss.app.repository.StudentRepository;
import com.jss.app.repository.TutorRepository;
import com.jss.app.repository.UserRepository;
import com.jss.app.service.LoginService;
import com.jss.app.service.StudentService;
import com.jss.app.spec.StudentSpecs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class PostTutorApplication implements ApplicationRunner {

	@Autowired
	private SchoolRepository schoolRepository;
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private InstituteRepository instituteRepository;

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private StudentCourseRepository studentCourseRepository;

	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private UserRepository userRepository;
	private Page<Student> findByStudnoContainingAndGroup_id;

	public static void main(String[] args) {
		SpringApplication.run(PostTutorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		initData();

	}

	@Transactional
	private void initData() {
		Sort sort = new Sort(Sort.Direction.ASC, "studno");
		Pageable pageable = PageRequest.of(0, 10, sort);
	}

}
