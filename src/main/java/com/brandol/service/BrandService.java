package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.BrandConverter;
import com.brandol.domain.*;
import com.brandol.domain.mapping.Community;
import com.brandol.domain.mapping.CommunityImage;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.request.BrandRequestDto;
import com.brandol.dto.response.BrandResponseDto;
import com.brandol.repository.*;
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


    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final MemberBrandRepository memberBrandRepository;
    private final FandomRepository fandomRepository;
    private final FandomImageRepository fandomImageRepository;
    private final ContentsRepository contentsRepository;
    private final ContentImageRepository   contentImageRepository;
    private final CommunityRepository communityRepository;
    private final CommunityImageRepository communityImageRepository;
    private final AmazonS3Manager s3Manager;

    @Transactional
    public Brand createBrand(BrandRequestDto.addBrand request){ // 브랜드 등록 함수

        //Brand brand = AddBrandRequest.toEntity(request); // dto에서 이름,설명 데이터만 우선으로 엔티티로 변환
        Brand brand = BrandConverter.toBrandEntity(request);
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

    public BrandResponseDto.BrandContentsDto makeBrandContentsBody(Long brandId){

        Brand targetBrand = brandRepository.findOneById(brandId);

        //콘텐츠 이벤트 리스트
        List<Contents> eventList = contentsRepository.findRecentEvents(brandId,PageRequest.of(0,2));
        //콘텐츠 카드뉴스 리스트
        List<Contents> cardNewsList = contentsRepository.findRecentCardNews(brandId,PageRequest.of(0,2));
        // 콘텐츠 비디오 리스트
        List<Contents> videoList = contentsRepository.findRecentVideos(brandId,PageRequest.of(0,2));

        /*더미 데이터*/
        //어드민 멤버

        Member adminMember = Member.builder()
                .name(targetBrand.getName()+"관리자")
                .avatar(targetBrand.getBackgroundImage())
                .build();

        List<BrandResponseDto.BrandContentsEventDto> contentsEventsDtoList = new ArrayList<>();
        for(int i=0; i<eventList.size();i++){
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(eventList.get(i).getId());
            List<String> contentsImageUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            BrandResponseDto.BrandContentsEventDto dto = BrandConverter.toBrandContentsEventDto(eventList.get(i),contentsImageUrlList,adminMember);
            contentsEventsDtoList.add(dto);
        }

        List<BrandResponseDto.BrandContentsCardNewsDto> contentsCardNewsDtoList = new ArrayList<>();
        for(int i=0; i<cardNewsList.size();i++){
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(cardNewsList.get(i).getId());
            List<String> contentsImageUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            BrandResponseDto.BrandContentsCardNewsDto dto = BrandConverter.toBrandContentsCardNewsDto(cardNewsList.get(i),contentsImageUrlList,adminMember);
            contentsCardNewsDtoList.add(dto);
        }

        List<BrandResponseDto.BrandContentsVideoDto> contentsVideoDtoList = new ArrayList<>();
        for(int i=0; i<videoList.size();i++){
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(videoList.get(i).getId());
            List<String> contentsImageUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            BrandResponseDto.BrandContentsVideoDto dto = BrandConverter.toBrandContentsVideoDto(videoList.get(i),contentsImageUrlList,adminMember);
            contentsVideoDtoList.add(dto);
        }
        return BrandConverter.toBrandContentsDto(contentsEventsDtoList,contentsCardNewsDtoList,contentsVideoDtoList);
    }


    public BrandResponseDto.BrandCommunityDto makeCommunityBody(Long brandId){

        Brand brand =brandRepository.findOneById(brandId);

        List<Community> freeBoardList = communityRepository.findRecentFreeBoard(brandId,PageRequest.of(0,2));
        List<Community> feedBackBoardList = communityRepository.findRecentFeedBackBoard(brandId,PageRequest.of(0,2));

        // 커뮤니티 자유 게시판
        List<BrandResponseDto.BrandCommunityBoardDto> freeBoardDtoList = new ArrayList<>();
        for(int i=0; i<freeBoardList.size();i++){
            Member member = freeBoardList.get(i).getMember();
            List<CommunityImage> communityImages = communityImageRepository.findAllByCommunityId(freeBoardList.get(i).getId());
            List<String> communityImageUrlList = communityImages.stream().map(CommunityImage::getImage).collect(Collectors.toList());
            BrandResponseDto.BrandCommunityBoardDto dto = BrandConverter.toBrandCommunityBoardDto(freeBoardList.get(i),communityImageUrlList,member);
            freeBoardDtoList.add(dto);
        }

        // 커뮤니티 피드백 게시판
        List<BrandResponseDto.BrandCommunityBoardDto> feedBackBoardDtoList = new ArrayList<>();
        for(int i=0; i<feedBackBoardList.size();i++){
            Member member = feedBackBoardList.get(i).getMember();
            List<CommunityImage> communityImages = communityImageRepository.findAllByCommunityId(feedBackBoardList.get(i).getId());
            List<String> communityIageUrlList = communityImages.stream().map(CommunityImage::getImage).collect(Collectors.toList());
            BrandResponseDto.BrandCommunityBoardDto dto = BrandConverter.toBrandCommunityBoardDto(feedBackBoardList.get(i),communityIageUrlList,member);
            feedBackBoardDtoList.add(dto);
        }

        return BrandConverter.toBrandCommunityDto(freeBoardDtoList,feedBackBoardDtoList);
    }

    @Transactional
    public Long createCommunity(BrandRequestDto.addCommunity communityDto,Long brandId,Long memberId){

        Member member = memberRepository.findOneById(memberId);
        if(member == null){throw  new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER);}
        Brand brand = brandRepository.findOneById(brandId);

        //커뮤니티 저장부
        Community community = Community.builder()
                .communityType(communityDto.getCommunityType())
                .title(communityDto.getTitle())
                .content(communityDto.getContent())
                .brand(brand)
                .member(member)
                .build();
        Community result= communityRepository.save(community);

        // 커뮤니티 이미지 저장부
        for(int i=0; i<communityDto.getImages().size();i++){
            String fileName = communityDto.getImages().get(i).getOriginalFilename();
            String fileUUID =s3Manager.createFileName(fileName);
            String fileURL = s3Manager.uploadFile(fileUUID,communityDto.getImages().get(i));

            CommunityImage communityImage = CommunityImage.builder()
                    .community(community)
                    .image(fileURL)
                    .build();
            communityImageRepository.save(communityImage);
        }
        return  result.getId();
    }

}
