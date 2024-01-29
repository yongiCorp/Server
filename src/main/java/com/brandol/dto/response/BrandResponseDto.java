package com.brandol.dto.response;

import com.brandol.domain.mapping.MemberBrandList;
import lombok.*;
import org.joda.time.DateTime;

import java.time.LocalDate;


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


}
