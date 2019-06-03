package com.jss.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.dictionary.Term;
import com.jss.app.repository.QuizAnswerRepository;

@Service
@Transactional(readOnly = true)
public class SatisticService {

	@Autowired
	private QuizAnswerRepository quizAnswerRepository;

	public List<Map<String, Object>> satisticRecent5yearByTutorId(Long tutorId) {

		List<Map<String, Object>> listEvaluation = new ArrayList<>();

		// 得到该导师5年内的所有课程Id
		Calendar nowTime = Calendar.getInstance();
		Integer year = nowTime.get(Calendar.YEAR);

		for (int y = 0; y < 5; y++) { // 年份从远到近
			for (int t = 0; t < 2; t++) { // 学期
				int curYear = year - 5 + y;
				Map<String, Object> evalutateRecentYearAndTerm = quizAnswerRepository
						.evalutateRecentYearAndTerm(tutorId, curYear, t);
				Map<String, Object> copyMap = copyMap(evalutateRecentYearAndTerm);
				copyMap.put("name", curYear + "年" + Term.values()[t].toString());
				listEvaluation.add(copyMap);
			}
		}

		return listEvaluation;
	}

	public List<Map<String, Object>> satisticByTutor(Boolean asc) {

		if (asc) {
			return quizAnswerRepository.evalutateByTutor();
		} else {
			return quizAnswerRepository.evalutateByTutorDesc();
		}
	}

	public List<Map<String, Object>> satisticByTutorAndQuestion(Boolean asc, Integer question) {

		if (asc) {
			return quizAnswerRepository.evalutateByTutorAndQuestion(question);
		} else {
			return quizAnswerRepository.evalutateByTutorAndQuestionDesc(question);
		}

	}

	public List<Map<String, Object>> satisticByInstitute(Boolean asc) {

		if (asc) {
			return quizAnswerRepository.evalutateByInstitute();
		} else {
			return quizAnswerRepository.evalutateByInstituteDesc();
		}

	}

	private Map<String, Object> copyMap(Map<String, Object> evaluation) {
		Map<String, Object> map = new HashMap<>();
		map.putAll(evaluation);
		return map;
	}

}
