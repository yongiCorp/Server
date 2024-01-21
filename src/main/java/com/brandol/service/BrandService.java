package com.brandol.service;

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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@ToString
public class BrandService {

    private final BrandRepository brandRepository;
    private final AmazonS3Manager s3Manager;

    public Brand findOneById(Long brandId){
        return brandRepository.findByOneId(brandId);
    } // 브랜드 Id로 브랜드 엔티티 조회

    public List<Brand>findRecentBrands(int cnt){
        return brandRepository.findRecentBrands(cnt);
    } // 최근 등록 브랜드 cnt개를 조회

    public List<Brand> getMainBannerBrands(){ // 페이지 1 메인배너에 들어갈 브랜드 5개를가져오는 함수
        List<Brand> result = new ArrayList<>();
        Brand brandol = brandRepository.findBrandByName("brandol"); //운영 방침상 브랜돌은 항상 첫번째 배너에 들어가야함 PM 운영 지침
        result.add(brandol);
        List<Brand> brandList = brandRepository.findRecentBrandsExceptForBrandol(4); // 브랜돌을 제외한 나머지 최신 4개 브랜드 추가

        int len = brandList.size();
        for(int i=0; i<len; i++){
            result.add(brandList.get(i));
        }

        return result;

    }

    public List<Brand> findRecentBrandsExceptForBrandol(int cnt){ // 브랜돌을 제외한 초신 등록 브랜드 cnt개를 가져오는 함수
        return brandRepository.findRecentBrandsExceptForBrandol(cnt);
    }


    public Brand createBrand(AddBrandRequest request){ // 브랜드 등록 함수

        Brand brand = AddBrandRequest.toEntity(request); // dto에서 이름,설명 데이터만 우선으로 엔티티로 변환

        String profileName = request.getProfileImage().getOriginalFilename(); // dto에 담긴 포로필 파일명 추출
        String profileIMGExtension = profileName.substring(profileName.lastIndexOf(".")+1); // 프로필 파일명에서 확자자 추출 (jpg/png)
        String profileUUID = UUID.randomUUID().toString() +"."+profileIMGExtension; //uuid + . + 확장자 => 파일명 생성
        String profileURL = s3Manager.uploadFile(profileUUID, request.getProfileImage()); // S3 해당 파일명으로 파일 업로드


        String backgroundName = request.getBackgroundImage().getOriginalFilename();
        String backgroundExtension = backgroundName.substring(profileName.lastIndexOf(".")+1);
        String backgroundUUID = UUID.randomUUID().toString()+"."+backgroundExtension;
        String backgroundURL = s3Manager.uploadFile(backgroundUUID, request.getBackgroundImage());

        brand.addImages(profileURL,backgroundURL); // 프로필, 배경 이미지의 url 주소를 엔티티에 할당
        Long savedBrandId = brandRepository.save(brand);

        return brandRepository.findByOneId(savedBrandId); // 디비에 저장

    }

    public boolean isExistBrand(Long id){
        return brandRepository.isExistBrand(id);
    }


}
