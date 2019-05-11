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
				List<Long> answerIds = quizAnswerRepository.evaluateSpecific(tutorId, curYear, t);
				// 使用In语句,数据必须有
				if (answerIds.isEmpty()) {
					Map<String, Object> evaluation = new HashMap<>();
					evaluation.put("name", curYear + "年" + Term.values()[t].toString());
					evaluation.put("value", 0);
					evaluation.put("A", 0);
					evaluation.put("B", 0);
					evaluation.put("C", 0);
					evaluation.put("D", 0);
					listEvaluation.add(evaluation);
					continue;
				}

				Map<String, Object> evaluation = quizAnswerRepository.evalutateQuizAnswer(answerIds);
				Map<String, Object> copyMap = copyMap(evaluation);

				copyMap.put("name", curYear + "年" + Term.values()[t].toString());
				Double amount = Double.parseDouble(copyMap.get("amount").toString());
				Integer numA = Integer.parseInt(copyMap.get("A").toString());
				String value = String.format("%.2f", numA / amount * 100);
				copyMap.put("value", value);
				listEvaluation.add(copyMap);
			}
		}

		return listEvaluation;
	}

	private Map<String, Object> copyMap(Map<String, Object> evaluation) {
		Map<String, Object> map = new HashMap<>();
		map.putAll(evaluation);
		return map;
	}

}
