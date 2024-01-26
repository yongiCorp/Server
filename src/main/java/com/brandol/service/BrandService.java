package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.domain.Brand;
import com.brandol.dto.request.AddBrandRequest;
import com.brandol.repository.BrandRepository;
import com.brandol.storagy.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@ToString
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;
    private final AmazonS3Manager s3Manager;

    public List<Brand>findRecentBrands(int cnt){
        return brandRepository.findRecentBrands(cnt);
    }


    public List<Brand> getMainBannerBrands(){
        List<Brand> result = new ArrayList<>();
        Brand brandol = brandRepository.findOneByName("brandol");
        if(brandol == null){
            throw new ErrorHandler(ErrorStatus._NOT_EXIST_BRAND);
        }
        result.add(brandol);
        List<Brand> brands = brandRepository.findRecentBrandsExceptForOne("brandol",4);

        int len = brands.size();
        for(int i=0; i<len; i++){
            result.add(brands.get(i));
        }
        return result;
    }

    @Transactional
    public Brand createBrand(AddBrandRequest request){ // 브랜드 등록 함수

        Brand brand = AddBrandRequest.toEntity(request); // dto에서 이름,설명 데이터만 우선으로 엔티티로 변환

        String profileName = request.getProfileImage().getOriginalFilename(); // dto에 담긴 포로필 파일명 추출
        if(profileName.length() == 0){ throw new ErrorHandler(ErrorStatus._FILE_NAME_ERROR);}
        String profileIMGExtension = profileName.substring(profileName.lastIndexOf(".")+1); // 프로필 파일명에서 확자자 추출 (jpg/png)
        String profileUUID = UUID.randomUUID()+"."+profileIMGExtension; //uuid + . + 확장자 => 파일명 생성
        String profileURL = s3Manager.uploadFile(profileUUID, request.getProfileImage()); // S3 해당 파일명으로 파일 업로드


        String backgroundName = request.getBackgroundImage().getOriginalFilename();
        if(backgroundName.length() == 0){ throw new ErrorHandler(ErrorStatus._FILE_NAME_ERROR);}
        String backgroundExtension = backgroundName.substring(profileName.lastIndexOf(".")+1);
        String backgroundUUID = UUID.randomUUID()+"."+backgroundExtension;
        String backgroundURL = s3Manager.uploadFile(backgroundUUID, request.getBackgroundImage());

        brand.addImages(profileURL,backgroundURL); // 프로필, 배경 이미지의 url 주소를 엔티티에 할당
        brandRepository.save(brand);
        Long savedBrandId = brand.getId();

        return brandRepository.findOneById(savedBrandId);
    }

    public boolean isExistBrand(Long id){
        return brandRepository.existsById(id);
    }

}
