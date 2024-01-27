package com.brandol.dto.request;

import com.brandol.domain.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Valid
public class AddBrandRequest{ // 브랜드 신규등록 DTO
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

    public static Brand toEntity(AddBrandRequest request){

        Brand brand = Brand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .profileImage(null)
                .backgroundImage(null)
                .build();
        return brand;
    }
}
