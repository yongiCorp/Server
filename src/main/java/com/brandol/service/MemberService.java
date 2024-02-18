package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.MemberConverter;
import com.brandol.domain.*;
import com.brandol.domain.enums.MemberListStatus;
import com.brandol.domain.mapping.*;
import com.brandol.dto.response.MemberResponseDto;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final MemberBrandRepository memberBrandRepository;
    private final ContentsRepository contentsRepository;
    private final ContentImageRepository contentImageRepository;
    private final FandomImageRepository fandomImageRepository;
    private final CommunityImageRepository communityImageRepository;
    private final FandomCommentRepository fandomCommentRepository;
    private final ContentsCommentRepository contentsCommentRepository;
    private final CommunityCommentRepository communityCommentRepository;

    @Transactional
    public Long addMemberBrandList(Long memberId, Long brandId){ //멤버가 멤버브랜드리스트에 브랜드를 추가 하는 함수

        Member member = memberRepository.findOneById(memberId);
        if(member == null){throw new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER);}

        Brand brand = brandRepository.findOneById(brandId);
        if(brand == null){
            throw new ErrorHandler(ErrorStatus._NOT_EXIST_BRAND);}

        MemberBrandList memberBrand = memberBrandRepository.findByMemberAndBrand(member, brand).orElse(null);
        if(memberBrand != null) {
            memberBrand.changeMemberListStatus(MemberListStatus.SUBSCRIBED);
            return memberBrand.getId();
        }

        Optional<MemberBrandList> allByBrand = memberBrandRepository.getAllByBrand(brand);
        Long count = allByBrand.stream().count();
        MemberBrandList build = MemberBrandList.builder()
                .memberListStatus(MemberListStatus.SUBSCRIBED)
                .member(member)
                .brand(brand)
                .sequence(count + 1)
                .build();
        memberBrandRepository.save(build);
        return build.getId();

    }

    public MemberResponseDto.MemberMainDto makeMemberMain(Long memberId){

        Member member = memberRepository.findOneById(memberId);
        if(member == null){throw new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER);}

        // 메인배너에 들어갈 브랜드들의 리스트
        Brand brandol = brandRepository.findOneByName("brandol");
        if(brandol == null){throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_BRANDOL_MAIN_BANNER);}
        List<Brand> brandList = brandRepository.findRecentBrandsExceptForOne("brandol",4);
        brandList.add(0,brandol);

        //서브배너에 들어갈 콘텐츠 리스트
        List<Contents> contentsList = contentsRepository.findRecentBrandEventsForSubBanner(PageRequest.of(0,10));

        //브랜드리스트에 들어갈 멤버-브랜드-리스트 타입의 리스트
        List<MemberBrandList> memberBrandLists = memberBrandRepository.findAllSubscribedByMemberId(memberId);

        List<MemberResponseDto.MainBannersDto> mainBannersDtoList = new ArrayList<>();
        for(int i=0; i<brandList.size();i++){
            MemberResponseDto.MainBannersDto dto = MemberConverter.toMainBannersDto(brandList.get(i));
            mainBannersDtoList.add(dto);
        }

        List<MemberResponseDto.SubBannersDto> subBannersDtoList = new ArrayList<>();
        for(int i=0; i<contentsList.size();i++){
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(contentsList.get(i).getId());
            List<String>imageUrls = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            MemberResponseDto.SubBannersDto dto = MemberConverter.toSubBannersDto(contentsList.get(i),imageUrls);
            subBannersDtoList.add(dto);
        }

        List<MemberResponseDto.MemberBrandListDto> memberBrandListDtoList = new ArrayList<>();
        for(int i=0; i<memberBrandLists.size();i++){
            MemberResponseDto.MemberBrandListDto dto = MemberConverter.toMemberBrandListDto(memberBrandLists.get(i));
            memberBrandListDtoList.add(dto);
        }

        return MemberConverter.toMemberMainDto(mainBannersDtoList,subBannersDtoList,memberBrandListDtoList);

    }

    public boolean existsByNickname(String value) {
        return memberRepository.existsByNickname(value);
    }

    public MemberResponseDto.MemberWrittenMainDto makeBrandMemberWrittenPage(Long memberId, Long brandId){

        Member member = memberRepository.findOneById(memberId);

        //특정 멤버가 작성한 특정 브랜드의 커뮤니티를 조회해서 createdAt을 기준으로 내림차순 정렬 -> top 2개 추출
        List<Community> communityListWrittenByMember = member.getCommunityList().stream().filter(c->c.getBrand().getId() == brandId).sorted(Comparator.comparing(Community::getCreatedAt).reversed()).collect(Collectors.toList());
        int communitySize = communityListWrittenByMember.size();
        List<Community> recentCommunityListWrittenByMember = communityListWrittenByMember.stream().limit(2).collect(Collectors.toList());

        List<MemberResponseDto.MemberWrittenDto> memberWrittenDtoList = new ArrayList<>();

        for(int i=0;i< recentCommunityListWrittenByMember.size();i++){
            Community targetCommunity = recentCommunityListWrittenByMember.get(i);
            List<CommunityImage> communityImages = communityImageRepository.findAllByCommunityId(targetCommunity.getId());
            List<String>imageUrlList = communityImages.stream().map(CommunityImage::getImage).collect(Collectors.toList());
            MemberResponseDto.MemberWrittenDto communityArticle = MemberResponseDto.MemberWrittenDto.builder()
                    .writerId(targetCommunity.getMember().getId())
                    .writerName(targetCommunity.getMember().getNickname())
                    .writerProfile(targetCommunity.getMember().getAvatar())
                    .articleInfo("Community:"+targetCommunity.getId())
                    .title(targetCommunity.getTitle())
                    .content(targetCommunity.getContent())
                    .images(imageUrlList)
                    .likeCount(targetCommunity.getLikes())
                    .commentCount(targetCommunity.getComments())
                    .writtenDate(targetCommunity.getCreatedAt())
                    .build();
            memberWrittenDtoList.add(communityArticle);
        }

        return MemberConverter.toMemberWrittenMainDto(communitySize, memberWrittenDtoList);
    }

    public MemberResponseDto.MemberWrittenMainDto makeBrandMemberWrittenCommentPage(Long memberId, Long brandId){

        List<FandomComment> fandomCommentList = fandomCommentRepository.findFandomCommentsByMemberIdAndBrandId(memberId,brandId); //해당 브랜드에 해당 멤버가 작성한 댓글 리스트
        Integer fandomCommentCount = fandomCommentList.size();
        List<ContentsComment> contentsCommentList = contentsCommentRepository.findContentsCommentByMemberIdAndBrandId(memberId,brandId); //해당 브랜드에 해당 멤버가 작성한 댓글 리스트
        Integer contentsCommentCount = contentsCommentList.size();
        List<CommunityComment> communityCommentList = communityCommentRepository.findCommunityCommentByMemberIdAndBrandId(memberId,brandId); //해당 브랜드에 해당 멤버가 작성한 댓글 리스트
        Integer communityCommentCount = communityCommentList.size();

        List<Object> masterList = new ArrayList<>(); // 댓글 리스트 합치기
        masterList.addAll(fandomCommentList);
        masterList.addAll(contentsCommentList);
        masterList.addAll(communityCommentList);

        Collections.sort(masterList, Comparator.comparing(this::getCommentCreatedAt).reversed()); // 댓글 리스트에서 시간 내림차순 정렬
        List<MemberResponseDto.MemberWrittenDto> memberWrittenCommentDtoList = new ArrayList<>(); // dto 리턴 리스트

        for(int i=0; i<masterList.size();i++){
            if(masterList.get(i) instanceof FandomComment){
                FandomComment targetFandomComment = (FandomComment) masterList.get(i);
                Fandom targetFandom = targetFandomComment.getFandom();
                List<FandomImage> fandomImageList = fandomImageRepository.findFandomImages(targetFandom.getId());
                List<String> imageUrlList = fandomImageList.stream().map(FandomImage::getImage).collect(Collectors.toList());

                MemberResponseDto.MemberWrittenDto fandomArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(targetFandom.getMember().getId())
                        .writerName(targetFandom.getMember().getName())
                        .writerProfile(targetFandom.getMember().getAvatar())
                        .articleInfo("Fandom:"+targetFandom.getId())
                        .title(targetFandom.getTitle())
                        .images(imageUrlList)
                        .likeCount(targetFandom.getLikes())
                        .commentCount(targetFandom.getComments())
                        .writtenDate(targetFandom.getCreatedAt())
                        .build();
                memberWrittenCommentDtoList.add(fandomArticle);
            } else if (masterList.get(i) instanceof ContentsComment) {
                ContentsComment targetContentsComment = (ContentsComment) masterList.get(i);
                Contents targetContents = targetContentsComment.getContents();
                List<ContentsImage> contentsImageList = contentImageRepository.findAllByContentsId(targetContentsComment.getId());
                List<String> imageUrlList = contentsImageList.stream().map(ContentsImage::getImage).collect(Collectors.toList());

                MemberResponseDto.MemberWrittenDto contentsArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(targetContents.getMember().getId())
                        .writerName(targetContents.getMember().getName())
                        .writerProfile(targetContents.getMember().getAvatar())
                        .articleInfo("Contents:"+targetContents.getId())
                        .title(targetContents.getTitle())
                        .images(imageUrlList)
                        .likeCount(targetContents.getLikes())
                        .commentCount(targetContents.getComments())
                        .writtenDate(targetContents.getCreatedAt())
                        .build();
                memberWrittenCommentDtoList.add(contentsArticle);

            } else if (masterList.get(i) instanceof  CommunityComment) {
                CommunityComment targetCommunityComment = (CommunityComment) masterList.get(i);
                Community tagetCommunity = targetCommunityComment.getCommunity();
                List<CommunityImage> communityImageList = communityImageRepository.findAllByCommunityId(targetCommunityComment.getId());
                List<String> imageUrlList = communityImageList.stream().map(CommunityImage::getImage).collect(Collectors.toList());

                MemberResponseDto.MemberWrittenDto communityArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(tagetCommunity.getMember().getId())
                        .writerName(tagetCommunity.getMember().getName())
                        .writerProfile(tagetCommunity.getMember().getAvatar())
                        .articleInfo("Community:"+tagetCommunity.getId())
                        .title(tagetCommunity.getTitle())
                        .images(imageUrlList)
                        .likeCount(tagetCommunity.getLikes())
                        .commentCount(tagetCommunity.getComments())
                        .writtenDate(tagetCommunity.getCreatedAt())
                        .build();

                memberWrittenCommentDtoList.add(communityArticle);
            }
        }
        Set<MemberResponseDto.MemberWrittenDto> memberWrittenDtoSet = memberWrittenCommentDtoList.stream().collect(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(MemberResponseDto.MemberWrittenDto::getArticleInfo)))); //게시글 중복제거
        List<MemberResponseDto.MemberWrittenDto> result = new ArrayList<>(memberWrittenDtoSet).stream().limit(2).collect(Collectors.toList()); //리스트 전환 (2건만 추출)
        Collections.sort(result, Comparator.comparing(MemberResponseDto.MemberWrittenDto::getWrittenDate).reversed());// 시간순으로 정렬
        Integer commentCount = fandomCommentCount + contentsCommentCount + communityCommentCount; //전체 댓글수 카운팅
        return MemberConverter.toMemberWrittenMainDto(commentCount,result);
    }


    private LocalDateTime getCommentCreatedAt(Object entity) {
        // 각 엔티티의 생성 날짜를 가져오는 메서드
        if (entity instanceof FandomComment) {
            return ((FandomComment) entity).getCreatedAt();
        } else if (entity instanceof ContentsComment) {
            return ((ContentsComment) entity).getCreatedAt();
        } else if (entity instanceof CommunityComment) {
            return ((CommunityComment) entity).getCreatedAt();
        }
        return null;
    }

    // 멤버의 아바타 및 닉네임 조회
    public MemberResponseDto.MemberAvatarDto getMemberAvatarNickname(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        return MemberConverter.toMemberAvatarDto(member);
    }

}
