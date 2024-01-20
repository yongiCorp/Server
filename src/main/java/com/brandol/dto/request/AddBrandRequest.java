package com.brandol.dto.request;

import com.brandol.domain.Brand;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddBrandRequest { // 브랜드 신규등록 DTO

   private String name;

   private String description;

   private MultipartFile profileImage;

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
