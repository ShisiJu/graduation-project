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

import com.jss.app.model.entity.Group;
import com.jss.app.model.entity.Student;

public class StudentSpecs {
	public static Specification<Student> searchStudent(String studno, Long groupId) {
		return new Specification<Student>() {
			private static final long serialVersionUID = 7583664073109352366L;

			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				List<Predicate> predicatesList = new ArrayList<>();

				if (!StringUtils.isEmpty(studno)) {
					Predicate studnoPredicate = builder.like(root.get("studno"), '%' + studno + '%');
					predicatesList.add(studnoPredicate);
				}

				// group 关联表查询
				if (groupId != null) {
					Join<Student, Group> joinGroup = root.join("group", JoinType.LEFT);
					Predicate groupPredicate = builder.equal(joinGroup.get("id"), groupId);
					predicatesList.add(groupPredicate);
				}

				// 把条件用 and 连接 并返回
				Predicate[] predicates = new Predicate[predicatesList.size()];
				return builder.and(predicatesList.toArray(predicates));
			}
		};
	}


}
