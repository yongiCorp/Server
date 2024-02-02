package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class BrandResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BrandHeaderDto {
        public BrandPreviewDto brandPreviewDto;
        public BrandUserStatus brandUserStatus;
    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BrandPreviewDto {
        private Long brand_id;
        private String brand_name;
        private String brand_description;
        private Integer brand_fan;
        private String brand_profile;
        private String brand_background;
    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BrandUserStatus {
        private Boolean isFan;
        private LocalDate join_date;
        private Long fan_sequence;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BrandFandomDto{
        public List<BrandFandomCultureDto> brandFandomCultureDtoList;
        public List<BrandFandomAnnouncementDto> brandFandomAnnouncementDtoList;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BrandFandomCultureDto{
        private Long writerId;
        private String writerName;
        private String writerProfile;
        private Long fandomId;
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
    public static class BrandFandomAnnouncementDto{
        private Long writerId;
        private String writerName;
        private String writerProfile;
        private Long fandomId;
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
    public static class BrandContentsDto{
        private List<BrandContentsEventDto> brandContentsEventDtoList;
        private List<BrandContentsCardNewsDto> brandContentsCardNewsDtoList;
        private List<BrandContentsVideoDto> brandContentsVideoDtoList;
    }


    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BrandContentsEventDto {

        private Long writerId;
        private String writerName;
        private String writerProfile;
        private Long contentsId;
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
    public static class BrandContentsCardNewsDto {

        private Long writerId;
        private String writerName;
        private String writerProfile;
        private Long contentsId;
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
    public static class BrandContentsVideoDto {

        private Long writerId;
        private String writerName;
        private String writerProfile;
        private Long contentsId;
        private String title;
        private String content;
        private List<String> images;
        private String video;
        private int likeCount;
        private int commentCount;
        private LocalDateTime writtenDate;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BrandCommunityBoardDto {
        private Long writerId;
        private String writerName;
        private String writerProfile;
        private Long communityId;
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
    public static class BrandCommunityDto{
        List<BrandCommunityBoardDto> brandCommunityBoardDtoList;
        List<BrandCommunityBoardDto> brandCommunityFeedBackBoardDtoList;
    }

}
