package com.brandol.dto.response;

import com.brandol.domain.mapping.MemberBrandList;
import lombok.*;
import org.joda.time.DateTime;

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
        private String title;
        private String content;
        private List<String> images;
        private int likeCount;
        private int commentCount;
        private LocalDateTime writtenDate;

    }

}
