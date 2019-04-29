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

import com.jss.app.model.entity.Institute;
import com.jss.app.model.entity.Tutor;

public class TutortSpecs {
	public static Specification<Tutor> searchTutor(String studno, Long instituteId) {
		return new Specification<Tutor>() {
			private static final long serialVersionUID = 7583664073109352366L;

			public Predicate toPredicate(Root<Tutor> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				List<Predicate> predicatesList = new ArrayList<>();

				if (!StringUtils.isEmpty(studno)) {
					Predicate studnoPredicate = builder.like(root.get("studno"), '%' + studno + '%');
					predicatesList.add(studnoPredicate);
				}

				// institute 关联表查询
				if (instituteId != null) {
					Join<Tutor, Institute> joinInstitute = root.join("institute", JoinType.LEFT);
					Predicate institutePredicate = builder.equal(joinInstitute.get("id"), instituteId);
					predicatesList.add(institutePredicate);
				}

				// 把条件用 and 连接 并返回
				Predicate[] predicates = new Predicate[predicatesList.size()];
				return builder.and(predicatesList.toArray(predicates));
			}
		};
	}


}
