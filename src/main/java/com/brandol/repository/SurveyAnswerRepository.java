package com.brandol.repository;

import com.brandol.domain.SurveyQuestion;
import com.brandol.domain.mapping.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
}
