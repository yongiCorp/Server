package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.BrandConverter;
import com.brandol.domain.Brand;
import com.brandol.domain.Fandom;
import com.brandol.domain.FandomImage;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.request.BrandRequestDto;
import com.brandol.dto.response.BrandResponseDto;
import com.brandol.repository.BrandRepository;
import com.brandol.repository.FandomImageRepository;
import com.brandol.repository.FandomRepository;
import com.brandol.repository.MemberBrandRepository;
import com.brandol.aws.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class BrandService {

    private final BrandRepository brandRepository;
    private final MemberBrandRepository memberBrandRepository;
    private final FandomRepository fandomRepository;
    private final FandomImageRepository fandomImageRepository;
    private final AmazonS3Manager s3Manager;

    @Transactional
    public Brand createBrand(BrandRequestDto.addBrand request){ // 브랜드 등록 함수

        //Brand brand = AddBrandRequest.toEntity(request); // dto에서 이름,설명 데이터만 우선으로 엔티티로 변환
        Brand brand = request.toEntity();
        System.out.println(brand.getName());
        System.out.println(brand.getDescription());
        String profileName = request.getProfileImage().getOriginalFilename(); // dto에 담긴 포로필 파일명 추출
        if(profileName==null){ throw new ErrorHandler(ErrorStatus._FILE_NAME_ERROR);}
        String profileUUID = s3Manager.createFileName(profileName);
        String profileURL = s3Manager.uploadFile(profileUUID, request.getProfileImage()); // S3 해당 파일명으로 파일 업로드


        String backgroundName = request.getBackgroundImage().getOriginalFilename();
        if(backgroundName==null){ throw new ErrorHandler(ErrorStatus._FILE_NAME_ERROR);}
        String backgroundUUID = s3Manager.createFileName(backgroundName);
        String backgroundURL = s3Manager.uploadFile(backgroundUUID, request.getBackgroundImage());

        brand.addImages(profileURL,backgroundURL); // 프로필, 배경 이미지의 url 주소를 엔티티에 할당
        brandRepository.save(brand);
        Long savedBrandId = brand.getId();

        return brandRepository.findOneById(savedBrandId);
    }


    public boolean isExistBrand(Long id){
        return brandRepository.existsById(id);
    }

    public BrandResponseDto.BrandHeaderDto makeBrandCommonHeader(Long brandId, Long memberId){

        //브랜드
        Brand targetBrand = brandRepository.findOneById(brandId);
        if (targetBrand == null){throw new ErrorHandler(ErrorStatus._NOT_EXIST_BRAND);}
        //멤버브랜드리스트
        MemberBrandList memberBrandList;
        List<MemberBrandList> memberBrandLists = memberBrandRepository.findOneByMemberIdAndBrandId(memberId,brandId);
        if(memberBrandLists.size()==1){
            memberBrandList = memberBrandLists.get(0); // 구독 기록이 존재하는 경우
        }
        else if(memberBrandLists.size() > 1 ){
            throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);
        }
        else {
            memberBrandList = null; // 기존에 구독하지 않았던 브랜드인 경우
        }
        //현재 팬 숫자
        int recentSubscriberCount = memberBrandRepository.getRecentSubscriberCount();
        // 헤더 생성

        BrandResponseDto.BrandPreviewDto brandPreviewDto = BrandConverter.toBrandPreviewDto(targetBrand, recentSubscriberCount);
        BrandResponseDto.BrandUserStatus brandUserStatus = BrandConverter.toUserStatusFromUser(memberBrandList);
        return BrandConverter.toBrandHeaderDto(brandPreviewDto,brandUserStatus);
    }

    public BrandResponseDto.BrandFandomDto makeBrandFandomBody(Long brandId){

        Brand targetBrand = brandRepository.findOneById(brandId);
        if(targetBrand==null){throw new ErrorHandler(ErrorStatus._NOT_EXIST_BRAND);}
        //팬덤 컬처 리스트
        List<Fandom> fandomCultureList = fandomRepository.getSomeRecentFandomCultures(brandId, PageRequest.of(0,2));
        // 팬덤 노티스 리스트
        List<Fandom> fandomAnnouncementList = fandomRepository.getSomeRecentFandomNotices(brandId, PageRequest.of(0,2));

        /*더미 데이터*/
        //어드민 멤버
        Member adminMember = Member.builder()
                .name(targetBrand.getName()+"관리자")
                .avatar(targetBrand.getBackgroundImage())
                .build();


        // 팬덤컬처 dto 응답 생성부
        List<BrandResponseDto.BrandFandomCultureDto> fandomCultureDtoList=new ArrayList<>();
        for(int i=0; i<fandomCultureList.size();i++){
            List<FandomImage> fandomImages = fandomImageRepository.findFandomImages(fandomCultureList.get(i).getId());
            List<String> fandomImageUrlList = fandomImages.stream().map(FandomImage::getImage).collect(Collectors.toList());
            BrandResponseDto.BrandFandomCultureDto dto = BrandConverter.toBrandFandomCultureDto(fandomCultureList.get(i),fandomImageUrlList,adminMember);
            fandomCultureDtoList.add(dto);
        }

        // 팬덤아나운스먼트 dto 응답 생성부
        List<BrandResponseDto.BrandFandomAnnouncementDto> fandomAnnouncementDtoList =new ArrayList<>();
        for(int i=0; i<fandomAnnouncementList.size();i++){
            List<FandomImage>fandomImages = fandomImageRepository.findFandomImages(fandomAnnouncementList.get(i).getId());
            List<String> fandomImageUrlList = fandomImages.stream().map(FandomImage::getImage).collect(Collectors.toList());
            BrandResponseDto.BrandFandomAnnouncementDto dto = BrandConverter.toBrandFandomAnnouncementDto(fandomAnnouncementList.get(i),fandomImageUrlList,adminMember);
            fandomAnnouncementDtoList.add(dto);
        }

        return BrandConverter.toBrandFandomDto(fandomCultureDtoList,fandomAnnouncementDtoList);
    }

}
