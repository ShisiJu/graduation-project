package com.jss.app.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.dictionary.Sex;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Student;
import com.jss.app.model.entity.User;
import com.jss.app.repository.GroupRepository;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.repository.StudentRepository;
import com.jss.app.repository.UserRepository;
import com.jss.app.spec.StudentSpecs;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;

	public Long countStudents() {
		return studentRepository.count();
	}

	public Page<Student> findAllWithPage(Integer index, Integer pageSize) {
		Sort sort = new Sort(Sort.Direction.ASC, "studno");
		Pageable pageable = PageRequest.of(index - 1, pageSize, sort);
		Page<Student> findAll = studentRepository.findAll(pageable);
		return findAll;
	}

	/**
	 * 动态查询,传入null,不进行查询 index在service进行了-1处理
	 * 
	 * @param studno
	 * @param groupId
	 * @param index
	 * @param pageSize
	 * @return
	 */
	public Page<Student> searchStudents(String studno, Long groupId, Integer index, Integer pageSize) {
		Sort sort = new Sort(Sort.Direction.ASC, "studno");
		Pageable pageable = PageRequest.of(index - 1, pageSize, sort);
		return studentRepository.findAll(StudentSpecs.searchStudent(studno, groupId), pageable);
	}

	public Student findStudentById(Long id) throws NoSuchElementException {

		return studentRepository.findById(id).get();
	}

	public List<Student> findStudentByGroup(Group group) {

		return studentRepository.findByGroup(group);
	}

	public List<Student> findAllStudents() {
		return studentRepository.findAll();
	}

	@Transactional
	public void deleteStudentById(Long id) {
		Optional<Student> student = studentRepository.findById(id);
		userRepository.deleteByStudno(student.get().getStudno());
		studentRepository.deleteById(id);
	}

	@Transactional
	public Student insertStudent(Student student) {

		return studentRepository.save(student);
	}

	@Transactional
	public Student insertStudent(Student student, Long groupId) {

		return updateStudent(student, groupId);
	}

	// 先要获得实体对象,再进行更新
	@Transactional
	public Student updateStudent(Student student) {
		// save()方法通过 判断ID是否存在来决定是否更新
		return studentRepository.save(student);
	}

	// 先要获得实体对象,再进行更新
	@Transactional
	public Student updateStudent(Student student, @Nullable Long groupId) {

		if (groupId != null) {
			Optional<Group> group = groupRepository.findById(groupId);
			student.setGroup(group.get());
		}

		Long id = student.getId();

		if (id == null) {
			String studno = student.getStudno();
			User user = User.builder().studno(studno).username(studno).pwd(studno).type(0).build();
			userRepository.save(user);
		}

		return studentRepository.save(student);
	}

}
