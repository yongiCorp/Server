package com.brandol.dto.request;

import com.brandol.domain.Brand;
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

    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private MultipartFile profileImage;
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
