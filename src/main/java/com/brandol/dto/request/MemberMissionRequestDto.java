package com.brandol.dto.request;

import com.brandol.domain.enums.QuestionType;
import com.brandol.validation.annotation.ExistBrand;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MemberMissionRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Setter
    public static class addMemberBrand {
        private List<questionResponse> questionResponses;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Setter
    public static class questionResponse {
        private Long surveyQuestionID;
        private QuestionType surveyQuestionType;
        private String response;
    }
}
