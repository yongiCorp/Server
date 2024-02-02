package com.brandol.dto.request;

import com.brandol.domain.Brand;
import com.brandol.domain.enums.CommunityType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class BrandRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class addBrand {
        @Schema(description = "등록할 브랜드의 이름")
        @NotNull
        private String name;
        @Schema(description = "등록할 브랜드의 소개")
        @NotNull
        private String description;
        @Schema(description = "등록할 브랜드의 프로필 이미지")
        @NotNull
        private MultipartFile profileImage;
        @Schema(description = "등록할 브랜드의 배경 이미지")
        @NotNull
        private MultipartFile backgroundImage;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class addCommunity{

        @NotNull
        @JsonProperty("communityType")
        private CommunityType communityType;
        @NotNull
        private String title;
        @NotNull
        private String content;

        private List<MultipartFile> images;
    }
}
