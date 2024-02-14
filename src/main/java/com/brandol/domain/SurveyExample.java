package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SurveyExample extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_example_id")
    private Long id;

    private String example;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="survey_question_id", nullable = false)
    private SurveyQuestion surveyQuestion;
}
