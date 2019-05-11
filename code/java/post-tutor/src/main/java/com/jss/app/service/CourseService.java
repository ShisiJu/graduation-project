package com.jss.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.alibaba.fastjson.JSONObject;
import com.github.wenhao.jpa.Specifications;
import com.jss.app.model.dictionary.Term;
import com.jss.app.model.entity.Course;
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

	public Page<StudentCourse> searchStudentCourseByStudent(JSONObject jsonObject) {

		Long studentId = jsonObject.getLong("studentId");

		Integer academicYear = jsonObject.getInteger("year");

		Term term = null;
		if (jsonObject.getString("term") != null) {
			term = Term.valueOf(jsonObject.getString("term"));
		}

		Specification<StudentCourse> specification = Specifications.<StudentCourse>and().eq("student.id", studentId)
				.eq(academicYear != null, "course.academicYear", academicYear).eq(term != null, "course.term", term).build();

		Integer index = jsonObject.getInteger("index");
		Integer pageSize = jsonObject.getInteger("pageSize");
		Sort sort = new Sort(Sort.Direction.DESC, "course.academicYear", "course.term");
		Pageable page = PageRequest.of(index - 1, pageSize, sort);

		return studentCourseRepository.findAll(specification, page);
	}

	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	public List<StudentCourse> findStudentCourseByStudent_Id(Long id) {

		return studentCourseRepository.findByStudent_Id(id);
	}

	public List<Course> findTutorCourseByTutor(Long id) {

		return courseRepository.findByTutor_id(id);

	}

	public Page<Course> searchCourses(JSONObject joCourse) {

		String name = joCourse.getString("name");

		List<Long> instituteIds = null;
		boolean boolIns = false;

		if (joCourse.getJSONArray("instituteIds") != null) {
			instituteIds = joCourse.getJSONArray("instituteIds").toJavaList(Long.class);
			boolIns = !instituteIds.isEmpty();
		}

		Integer index = joCourse.getInteger("index");
		Integer pageSize = joCourse.getInteger("pageSize");
		Integer academicYear = joCourse.getInteger("academicYear");
		Optional<Long> tutorId = Optional.ofNullable(joCourse.getLong("tutorId"));

		Term term = null;
		if (joCourse.getString("term") != null) {
			term = Term.valueOf(joCourse.getString("term"));
		}

		Sort sort = new Sort(Sort.Direction.DESC, "academicYear", "term");
		Pageable pageable = PageRequest.of(index - 1, pageSize, sort);

		Specification<Course> specification = Specifications.<Course>and()
				.like(!StringUtils.isEmpty(name), "name", "%" + name + "%")
				.eq(academicYear != null, "academicYear", academicYear).eq(term != null, "term", term)
				.in(boolIns, "tutor.institute.id", boolIns ? instituteIds.toArray() : null)
				.eq(tutorId.isPresent(), "tutor.id", tutorId.orElse(null)).build();

		return courseRepository.findAll(specification, pageable);
	}

	public List<Course> searchCoursesWithoutPage(JSONObject joCourse) {

		String name = joCourse.getString("name");

		List<Long> instituteIds = null;
		boolean boolIns = false;

		if (joCourse.getJSONArray("instituteIds") != null) {
			instituteIds = joCourse.getJSONArray("instituteIds").toJavaList(Long.class);
			boolIns = !instituteIds.isEmpty();
		}

		Integer academicYear = joCourse.getInteger("academicYear");
		Optional<Long> tutorId = Optional.ofNullable(joCourse.getLong("tutorId"));

		Term term = null;
		if (joCourse.getString("term") != null) {
			term = Term.valueOf(joCourse.getString("term"));
		}

		Integer lowYear = joCourse.getInteger("beforeYear");
		Integer highYear = joCourse.getInteger("afterYear");
		boolean betweenYear = lowYear != null && highYear != null;

		Specification<Course> specification = Specifications.<Course>and()
				.like(!StringUtils.isEmpty(name), "name", "%" + name + "%")
				.eq(academicYear != null, "academicYear", academicYear).eq(term != null, "term", term)
				.in(boolIns, "tutor.institute.id", boolIns ? instituteIds.toArray() : null)
				.eq(tutorId.isPresent(), "tutor.id", tutorId.orElse(null))
				.between(betweenYear, "academicYear", lowYear, highYear).build();

		return courseRepository.findAll(specification);
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
		Course savedCourse = courseRepository.save(course);
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

		return savedCourse;
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

	public List<Long> findGroupsByCourseId(Long courseId) {

		List<BigInteger> groupId = courseRepository.findGroupIdsByCourseId(courseId);
		return DataBaseConvertor.toListLong(groupId);
	}

	@Transactional
	public List<Long> insertedStudentIds(Long courseId, List<Long> newGroupIds) {

		List<Long> oldGroupIds = findGroupsByCourseId(courseId);

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
