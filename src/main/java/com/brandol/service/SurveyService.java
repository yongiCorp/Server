package com.brandol.service;

import com.brandol.apiPayload.code.BaseCode;
import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.domain.Member;
import com.brandol.domain.SurveyQuestion;
import com.brandol.domain.mapping.SurveyAnswer;
import com.brandol.dto.request.MemberMissionRequestDto;
import com.brandol.repository.MemberRepository;
import com.brandol.repository.SurveyAnswerRepository;
import com.brandol.repository.SurveyQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService {
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final MemberRepository memberRepository;

    public List<SurveyQuestion> getSurveyQuestions(Long missionId) {
//        List<SurveyQuestion> surveyQuestions = surveyQuestionRepository.findByMissionIdWithSurveyExamples(missionId);
//        for (SurveyQuestion surveyQuestion : surveyQuestions) {
//            System.out.println("surveyQuestion.getQuestion() = " + surveyQuestion.getQuestion());
//            System.out.println("surveyQuestion.getSurveyExampleList() = " + surveyQuestion.getSurveyExampleList());
//        }
//        return surveyQuestions;
        return surveyQuestionRepository.findAllByMissionId(missionId);
    }
    @Transactional
    public void submitAnswer(Long memberId, Long missionId, MemberMissionRequestDto.addMemberBrand request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAllByMissionId(missionId);
        // 요청 데이터의 설문 문항 응답 ID 리스트 생성
        List<Long> requestQuestionIds = request.getQuestionResponses().stream()
                .map(MemberMissionRequestDto.questionResponse::getSurveyQuestionID)
                .collect(Collectors.toList());

        // 설문 문항 리스트의 모든 ID가 요청 데이터에 포함되어 있는지 확인
        surveyQuestionList.forEach(surveyQuestion -> {
            Long surveyQuestionID = surveyQuestion.getId();
            if (!requestQuestionIds.contains(surveyQuestionID)) {
                throw new ErrorHandler(ErrorStatus._EMPTY_SURVEY);
            }
            // 해당 ID에 대한 응답을 찾아서 SurveyAnswer 객체 생성 후 저장
            MemberMissionRequestDto.questionResponse matchingResponse = request.getQuestionResponses().stream()
                    .filter(response -> response.getSurveyQuestionID().equals(surveyQuestionID))
                    .findFirst()
                    .get();
            SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                    .member(member)
                    .surveyQuestion(surveyQuestion)
                    .answer(matchingResponse.getResponse())
                    .build();
            surveyAnswerRepository.save(surveyAnswer);
        });

    }
}
