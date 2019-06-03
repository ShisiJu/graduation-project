package com.jss.app.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.jss.app.model.entity.Course;
import com.jss.app.model.m2m.StudentCourse;

public class StudentCourseSpecs {
	public static Specification<StudentCourse> searchStudentCourse(String tutorStudno, Long groupId) {

		/*
		 * Specification<StudentCourse> specification =
		 * Specifications.<StudentCourse>and() .eq(studentId != null, "student.id",
		 * studentId) .like(!StringUtils.isEmpty(name), "course.name", "%" + name + "%")
		 * .eq(academicYear != null, "course.academicYear", academicYear).eq(term !=
		 * null, "course.term", term) .eq(courseTyoe != null, "course.courseTyoe",
		 * courseTyoe) .eq(!StringUtils.isEmpty(studentStudno), "student.studno",
		 * studentStudno) .eq(!StringUtils.isEmpty(tutorStudno), "course.tutor.studno",
		 * tutorStudno).build();
		 * 
		 */

		return new Specification<StudentCourse>() {

			private static final long serialVersionUID = 7583664073109352366L;

			public Predicate toPredicate(Root<StudentCourse> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Predicate> predicatesList = new ArrayList<>();

				if (!StringUtils.isEmpty(tutorStudno)) {
					Predicate studnoPredicate = builder.like(root.get("studno"), '%' + tutorStudno + '%');
					predicatesList.add(studnoPredicate);
				}

				// group 关联表查询
				if (groupId != null) {
					Join<StudentCourse, Course> joinCourse = root.join("course", JoinType.LEFT);
//					Predicate groupPredicate = builder.equal(joinGroup.get("id"), groupId);
//					predicatesList.add(groupPredicate);
				}

				// 把条件用 and 连接 并返回
				Predicate[] predicates = new Predicate[predicatesList.size()];
				return builder.and(predicatesList.toArray(predicates));
			}
		};
	}
}
