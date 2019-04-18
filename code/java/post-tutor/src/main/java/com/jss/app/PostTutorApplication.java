package com.jss.app;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jss.app.model.dictionary.CommonDictionary;
import com.jss.app.model.dictionary.Sex;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.School;
import com.jss.app.model.entity.Student;
import com.jss.app.model.entity.Tutor;
import com.jss.app.model.entity.User;
import com.jss.app.repository.GroupRepository;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.SchoolRepository;
import com.jss.app.repository.StudentRepository;
import com.jss.app.repository.TutorRepository;
import com.jss.app.repository.UserRepository;
import com.jss.app.service.LoginService;
import com.jss.app.service.StudentService;

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
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(PostTutorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		initData();

	}

	private void initData() {
		
		School school = new School();
		school.setCode("45500");
		school.setName("沈阳建筑大学");
		schoolRepository.save(school);

		Student student = Student.builder().name("赵丽颖").studno(1506420129L).sex(Sex.女).build();
		studentRepository.save(student);
		
		User user = User.builder().username("1506420129").pwd("1506420129").type(0).build();
		userRepository.save(user);
		
	}

}
