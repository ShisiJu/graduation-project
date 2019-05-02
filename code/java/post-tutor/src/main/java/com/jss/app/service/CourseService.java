package com.jss.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.wenhao.jpa.Specifications;
import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Course;
import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Student;
import com.jss.app.model.entity.Tutor;
import com.jss.app.model.m2m.StudentCourse;
import com.jss.app.repository.CourseRepository;
import com.jss.app.repository.StudentCourseRepository;
import com.jss.app.repository.StudentRepository;
import com.jss.app.repository.TutorRepository;
import com.jss.app.util.DataBaseConvertor;

@Service
@Transactional(readOnly = true)
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TutorRepository tutorRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	public List<StudentCourse> findStudentCourseByStudent_Id(Long id) {

		return studentCourseRepository.findByStudent_Id(id);
	}

	public List<Course> findTutorCourseByTutor(Long id) {

		return courseRepository.findByTutor_id(id);

	}

	public Page<Course> searchCourses(String name, List<Long> instituteIds, Integer index, Integer pageSize) {

		Sort sort = new Sort(Sort.Direction.ASC, "code");
		Pageable pageable = PageRequest.of(index - 1, pageSize, sort);

		boolean boolIns = !instituteIds.isEmpty();

		Specification<Course> specification = Specifications.<Course>and()
				.like(!StringUtils.isEmpty(name), "name", "%" + name + "%")
				.in(boolIns, "tutor.institute.id", instituteIds.toArray()).build();

		return courseRepository.findAll(specification, pageable);
	}

	// 级联删除Quiz和QuizAnswer
	@Transactional
	public void deleteCourseById(Long id) {

		courseRepository.deleteById(id);
	}

	// 保存时,要把所属组的 学生与课程关联起来
	@Transactional
	public Course saveCourse(Course course, @Nullable Long tutorId, @Nullable List<Long> newGroupIds) {

		if (tutorId != null) {
			Optional<Tutor> tutor = tutorRepository.findById(tutorId);
			course.setTutor(tutor.get());
		}

		if (newGroupIds != null) {
			Long courseId = course.getId();
			// 动态地添加学生,需要先将不再属于课程的group的学生删除
			List<Long> insertedStudentIds = insertedStudentIds(courseId, newGroupIds);
			// 先将原来的 CourseGroup 删除,再添加新的数据
			updateCourseGroup(courseId, newGroupIds);
			// 通过SQL,保存学生与课程的关系
			if (insertedStudentIds != null) {
				saveStudentCourseBySql(courseId, insertedStudentIds);
			}
		}

		return courseRepository.save(course);
	}

	@Transactional
	public Integer saveStudentCourseBySql(Long courseId, List<Long> studentIds) {

		if (studentIds.isEmpty())
			return 0;

		StringBuffer sb = new StringBuffer();
		sb.append("insert into T_STUDENT_COURSE(course_id,student_id,create_time,update_time) values");

		studentIds.forEach(sid -> {
			sb.append("(" + courseId + "," + sid + ",NOW(),NOW()) ,");
		});

		String sqlString = sb.substring(0, sb.length() - 1).toString();

		Query query = entityManager.createNativeQuery(sqlString);
		int executeUpdate = query.executeUpdate();

		return executeUpdate;
	}

	@Transactional
	public void updateCourseGroup(Long courseId, List<Long> groupIds) {

		// 删除原来数据
		courseRepository.deleteCourseGroupByCourseId(courseId);

		if (groupIds.isEmpty()) {
			return;
		}

		// 添加新的数据
		StringBuffer sb = new StringBuffer();
		sb.append("insert into t_course_group (course_id,group_id) values");

		groupIds.forEach(gid -> {
			sb.append("(" + courseId + "," + gid + ") ,");
		});

		String sqlString = sb.substring(0, sb.length() - 1).toString();

		Query query = entityManager.createNativeQuery(sqlString);
		query.executeUpdate();

	}

	public List<Long> insertedStudentIds(Long courseId, List<Long> newGroupIds) {

		List<BigInteger> groupId = courseRepository.findGroupIdsByCourseId(courseId);
		List<Long> oldGroupIds = DataBaseConvertor.toListLong(groupId);

		List<Long> deleteGroupIds = new ArrayList<>();
		List<Long> insertGroupIds = new ArrayList<>();

		oldGroupIds.forEach(id -> {
			deleteGroupIds.add(id);
		});

		newGroupIds.forEach(newId -> {
			boolean contained = oldGroupIds.contains(newId);
			if (contained == false) {
				insertGroupIds.add(newId);
			} else {
				// oldIds 需要除去交集
				deleteGroupIds.remove(newId);
			}
		});

		// 删除 不需要的学生-课程
		if (deleteGroupIds.isEmpty() == false) {
			List<Long> deleteStudentIds = DataBaseConvertor
					.toListLong(studentRepository.findStudentsIdsByGroupsIds(deleteGroupIds));
			if (deleteStudentIds.isEmpty() == false) {
				studentCourseRepository.deleteByStudent_idIn(deleteStudentIds);
			}
		}

		// 根据groupIds 找到所有学生
		if (insertGroupIds.isEmpty() == false) {
			List<Long> insertStudentIds = DataBaseConvertor
					.toListLong(studentRepository.findStudentsIdsByGroupsIds(insertGroupIds));
			return insertStudentIds;

		} else {

			return null;
		}
	}

}
