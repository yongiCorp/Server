package com.brandol.service;

import com.brandol.domain.SurveyQuestion;
import com.brandol.repository.SurveyQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService {
    private final SurveyQuestionRepository surveyQuestionRepository;

    public List<SurveyQuestion> getSurveyQuestions(Long missionId) {
//        List<SurveyQuestion> surveyQuestions = surveyQuestionRepository.findByMissionIdWithSurveyExamples(missionId);
//        for (SurveyQuestion surveyQuestion : surveyQuestions) {
//            System.out.println("surveyQuestion.getQuestion() = " + surveyQuestion.getQuestion());
//            System.out.println("surveyQuestion.getSurveyExampleList() = " + surveyQuestion.getSurveyExampleList());
//        }
//        return surveyQuestions;
        return surveyQuestionRepository.findAllByMissionId(missionId);
    }
}
