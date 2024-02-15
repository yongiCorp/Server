package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SurveyQuestion extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_question_id")
    private Long id;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id")
    private Mission mission;

    @OneToMany(mappedBy = "surveyQuestion")
    @JsonIgnore
    private List<SurveyExample> surveyExampleList = new LinkedList<>();

}
