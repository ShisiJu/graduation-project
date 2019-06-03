package com.jss.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.wenhao.jpa.Specifications;
import com.jss.app.model.dictionary.CourseTyoe;
import com.jss.app.model.dictionary.Term;
import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Student;
import com.jss.app.model.entity.Tutor;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.repository.CourseRepository;
import com.jss.app.repository.StudentCourseRepository;
import com.jss.app.repository.StudentRepository;
import com.jss.app.repository.TutorRepository;

@Service
@Transactional(readOnly = true)
public class StudentCourseService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	public Page<StudentCourse> searchStudentCourseByStudent(JSONObject jsonObject) {

		Specification<StudentCourse> specification = specification(jsonObject);
		Integer index = jsonObject.getInteger("index");
		Integer pageSize = jsonObject.getInteger("pageSize");
		Sort sort = new Sort(Sort.Direction.DESC, "course.academicYear", "course.term", "course.id");
		Pageable page = PageRequest.of(index - 1, pageSize, sort);

		return studentCourseRepository.findAll(specification, page);
	}

	public List<StudentCourse> searchStudentCourseByStudentWithoutPage(JSONObject jsonObject) {
		Specification<StudentCourse> specification = specification(jsonObject);
		Sort sort = new Sort(Sort.Direction.DESC, "course.academicYear", "course.term", "course.id");
		return studentCourseRepository.findAll(specification, sort);
	}

	@Transactional
	public void updateScore(Long id, Integer score) {
		// 课程的current_num 加1
		Optional<StudentCourse> studentCourseOp = studentCourseRepository.findById(id);

		StudentCourse studentCourse = studentCourseOp.get();
		studentCourse.setScore(score);

		studentCourseRepository.save(studentCourse);
	}

	@Transactional
	public void saveStudentCourse(Long studentId, Long courseId) {
		// 课程的current_num 加1
		Optional<Course> courseOp = courseRepository.findById(courseId);

		Course course = courseOp.get();

		if (course.getCourseType() == CourseTyoe.必修) {
			studentCourseRepository.insertByStduentIdAndCourseId(studentId, courseId);
			return;
		}

		Integer currentNum = course.getCurrentNum();
		Integer amount = course.getAmount();

		if (currentNum >= amount)
			return;

		course.setCurrentNum(currentNum + 1);
		courseRepository.save(course);
		studentCourseRepository.insertByStduentIdAndCourseId(studentId, courseId);
	}

	private Specification<StudentCourse> specification(JSONObject jsonObject) {

		if (jsonObject == null) {
			jsonObject = new JSONObject();
		}

		Long studentId = jsonObject.getLong("studentId");

		String studentStudno = jsonObject.getString("studentStudno");
		String tutorStudno = jsonObject.getString("tutorStudno");

		Integer academicYear = jsonObject.getInteger("academicYear");
		String name = jsonObject.getString("name");

		Term term = null;
		if (jsonObject.getString("term") != null) {
			term = Term.valueOf(jsonObject.getString("term"));
		}

		CourseTyoe courseTyoe = null;
		if (jsonObject.getString("courseTyoe") != null) {
			courseTyoe = CourseTyoe.valueOf(jsonObject.getString("courseTyoe"));
		}

		Tutor tutor = tutorRepository.findByStudno(tutorStudno);

		Specification<StudentCourse> specification = Specifications.<StudentCourse>and()
				.eq(studentId != null, "student.id", studentId)
				.like(!StringUtils.isEmpty(name), "course.name", "%" + name + "%")
				.eq(academicYear != null, "course.academicYear", academicYear).eq(term != null, "course.term", term)
				.eq(courseTyoe != null, "course.courseTyoe", courseTyoe)
				.eq(!StringUtils.isEmpty(studentStudno), "student.studno", studentStudno)
				.eq(!StringUtils.isEmpty(tutorStudno), "course.tutor", tutor).build();

		return specification;
	}

	@Transactional
	public void saveStudentCourse(String studno, String code) {
		// 课程的current_num 加1
		Course course = courseRepository.findByCode(code);
		Student student = studentRepository.findByStudno(studno);
		if (student == null || course == null)
			return;
		Long studentId = student.getId();
		Long courseId = course.getId();
		saveStudentCourse(studentId, courseId);
	}

	@Transactional
	public void save(StudentCourse studentCourse) {
		studentCourseRepository.save(studentCourse);
	}

	public StudentCourse findByStudent_StudnoAndCourse_Code(String studno, String code) {
		return studentCourseRepository.findByStudent_StudnoAndCourse_Code(studno, code);
	}

	@Transactional
	public void deleteStudentCourse(Long id) {
		// 课程的current_num 减 1
		Optional<StudentCourse> studentCourseOp = studentCourseRepository.findById(id);
		StudentCourse studentCourse = studentCourseOp.get();
		Course course = studentCourse.getCourse();
		int currentNum = course.getCurrentNum();
		course.setCurrentNum(currentNum - 1);
		courseRepository.save(course);
		studentCourseRepository.deleteById(id);
	}

	public Long countStudents() {
		return studentRepository.count();
	}

}
