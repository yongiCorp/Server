package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberMainDto{
        public List<MainBannersDto> mainBannersDtoList;
        public List<SubBannersDto> subBannersDtoList;
        public List<MemberBrandListDto> memberBrandListDtoList;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MainBannersDto{
        private Long brandId;
        private String brandBackgroundImage;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SubBannersDto{
        private Long contentId;
        private List<String> images;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberBrandListDto{
        private Long brandId;
        private String brandName;
        private String profileImage;
        private String brandDescription;//0216 추가
        private Long sequence;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)

    public static class MemberWrittenDto {

        private Long writerId;
        private String writerName;
        private String writerProfile;
        private String articleInfo;
        private String title;
        private String content;
        private List<String> images;
        private int likeCount;
        private int commentCount;
        private LocalDateTime writtenDate;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberWrittenMainDto {
        private Integer totalArticleCount;
        private List<MemberWrittenDto> memberWrittenDtoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED) // 빨간줄 떠서 내가 수정함 Required -> No by 종.
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberAvatarDto {
        private Long memberId;
        private String avatar;
        private String nickname;
    }

}
