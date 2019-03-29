package com.jss.app;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jss.app.model.dictionary.Sex;
import com.jss.app.model.entity.Student;
import com.jss.app.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class PostTutorApplication implements ApplicationRunner {
	@Autowired
	private StudentRepository studentRepository;
	public static void main(String[] args) {
		SpringApplication.run(PostTutorApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		initStudent();
		
	}

	private void initStudent() {
		Student stud = Student.builder().name("李大嘴").sex(Sex.男).build();
		studentRepository.save(stud);
		log.info(stud.toString());
		
		Iterable<Student> findAll = studentRepository.findAll();
		
		for (Student student : findAll) {
			log.info(student.toString());
		}
	}
}
