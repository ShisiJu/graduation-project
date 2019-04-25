package com.jss.app.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.dictionary.Sex;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Student;
import com.jss.app.repository.GroupRepository;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public Student findStudentById(Long id) throws NoSuchElementException {

		return studentRepository.findById(id).get();
	}

	public List<Student> findStudentByGroup(Group group) {
		
		return studentRepository.findByGroup(group);
	}
	
	public List<Student> findAllStudents(){
		return studentRepository.findAll();
	}

	public void deleteStudentById(Long id) {

		studentRepository.deleteById(id);
	}

	public Student insertStudent(Student student) {

		return studentRepository.save(student);
	}

	// 先要获得实体对象,再进行更新
	public Student updateStudent(Student student) {
		// save()方法通过 判断ID是否存在来决定是否更新
		return studentRepository.save(student);
	}

}
