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
        List<Contents> contentsList = contentsRepository.findRecentBrands(10);

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

    public MemberResponseDto.MemberWrittenMainDto makeMemberWrittenPage(Long memberId){

        Member member = memberRepository.findOneById(memberId);

        //특정 멤버가 작성한 팬덤 게시글을 조회해서 createdAt을 기준으로 내림차순 정렬 -> top 2개 추출
        List<Fandom> fandomListWrittenByMember = member.getFandomList().stream().sorted(Comparator.comparing(Fandom::getCreatedAt)).collect(Collectors.toList());
        int fandomSize = fandomListWrittenByMember.size();
        List<Fandom> recentFandomListWrittenByMember = fandomListWrittenByMember.stream().limit(2).collect(Collectors.toList());

        //특정 멤버가 작성한 콘텐츠를 조회해서 createdAt을 기준으로 내림차순 정렬 -> top 2개 추출
        List<Contents> contentsListWrittenByMember = member.getContentsList().stream().sorted(Comparator.comparing(Contents::getCreatedAt)).collect(Collectors.toList());
        int contentsSize = contentsListWrittenByMember.size();
        List<Contents> recentContentsListWrittenByMember = contentsListWrittenByMember.stream().limit(2).collect(Collectors.toList());

        //특정 멤버가 작성한 커뮤니티를 조회해서 createdAt을 기준으로 내림차순 정렬 -> top 2개 추출
        List<Community> communityListWrittenByMember = member.getCommunityList().stream().sorted(Comparator.comparing(Community::getCreatedAt)).collect(Collectors.toList());
        int communitySize = communityListWrittenByMember.size();
        List<Community> recentCommunityListWrittenByMember = communityListWrittenByMember.stream().limit(2).collect(Collectors.toList());

        // 각 게시판 별 가장 최신 article 각 2개씩(최대) 가져와 합친 List
        List<Object> masterList =new ArrayList<>();
        masterList.addAll(recentFandomListWrittenByMember);
        masterList.addAll(recentContentsListWrittenByMember);
        masterList.addAll(recentCommunityListWrittenByMember);

        // 각각의 article 들의 createdAt 필드를 기준으로 내림차순으로 정렬
        Collections.sort(masterList, Comparator.comparing(this::getArticleCreatedAt).reversed());

        // 가장 최신 두건의 article 들만 추출
        List<Object>resultArticleList = masterList.stream().limit(2).collect(Collectors.toList());

        List<MemberResponseDto.MemberWrittenDto> memberWrittenDtoList = new ArrayList<>();
        for(int i=0;i<resultArticleList.size();i++){

            //해당 객체가 Fandom 게시글 일 경우
            if(resultArticleList.get(i) instanceof Fandom){
                Fandom targetFandom = (Fandom) resultArticleList.get(i);
                List<FandomImage> fandomImages = fandomImageRepository.findFandomImages(targetFandom.getId());
                List<String>imageUrlList = fandomImages.stream().map(FandomImage::getImage).collect(Collectors.toList());
                MemberResponseDto.MemberWrittenDto fandomArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(targetFandom.getMember().getId())
                        .writerName(targetFandom.getMember().getNickname())
                        .writerProfile(targetFandom.getMember().getAvatar())
                        .articleType("Fandom")
                        .id(targetFandom.getId())
                        .title(targetFandom.getTitle())
                        .content(targetFandom.getContent())
                        .images(imageUrlList)
                        .likeCount(targetFandom.getLikes())
                        .commentCount(targetFandom.getComments())
                        .writtenDate(targetFandom.getCreatedAt())
                        .build();
                memberWrittenDtoList.add(fandomArticle);

            }

            // 해당 객체가 콘텐츠 게시글 일 경우
            else if(resultArticleList.get(i) instanceof Contents){
                System.out.println(((Contents) resultArticleList.get(i)).getTitle());
                Contents targetContents = (Contents) resultArticleList.get(i);
                List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(targetContents.getId());
                List<String>imageUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
                MemberResponseDto.MemberWrittenDto contentsArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(targetContents.getMember().getId())
                        .writerName(targetContents.getMember().getNickname())
                        .writerProfile(targetContents.getMember().getAvatar())
                        .articleType("Contents")
                        .id(targetContents.getId())
                        .title(targetContents.getTitle())
                        .content(targetContents.getContent())
                        .images(imageUrlList)
                        .likeCount(targetContents.getLikes())
                        .commentCount(targetContents.getComments())
                        .writtenDate(targetContents.getCreatedAt())
                        .build();
                memberWrittenDtoList.add(contentsArticle);
            }

            // 해당 게시글이 커뮤니티 게시글일 경우
            else if(resultArticleList.get(i) instanceof Community){
                System.out.println(((Community) resultArticleList.get(i)).getTitle());
                Community targetCommunity = (Community)resultArticleList.get(i);
                List<CommunityImage> communityImages = communityImageRepository.findAllByCommunityId(targetCommunity.getId());
                List<String>imageUrlList = communityImages.stream().map(CommunityImage::getImage).collect(Collectors.toList());
                MemberResponseDto.MemberWrittenDto communityArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(targetCommunity.getMember().getId())
                        .writerName(targetCommunity.getMember().getNickname())
                        .writerProfile(targetCommunity.getMember().getAvatar())
                        .articleType("Community")
                        .id(targetCommunity.getId())
                        .title(targetCommunity.getTitle())
                        .content(targetCommunity.getContent())
                        .images(imageUrlList)
                        .likeCount(targetCommunity.getLikes())
                        .commentCount(targetCommunity.getComments())
                        .writtenDate(targetCommunity.getCreatedAt())
                        .build();
                memberWrittenDtoList.add(communityArticle);
            }
        }
        Integer totalArticleCount = fandomSize + contentsSize + communitySize;
        return MemberConverter.toMemberWrittenMainDto(totalArticleCount, memberWrittenDtoList);
    }

    public MemberResponseDto.MemberWrittenMainDto makeMemberWrittenCommentPage(Long memberId){

        List<FandomComment> fandomCommentList = fandomCommentRepository.findFandomCommentsByMemberId(memberId);
        Integer fandomCommentCount = fandomCommentList.size();
        List<ContentsComment> contentsCommentList = contentsCommentRepository.findContentsCommentByMemberId(memberId);
        Integer contentsCommentCount = contentsCommentList.size();
        List<CommunityComment> communityCommentList = communityCommentRepository.findCommunityCommentByMemberId(memberId);
        Integer communityCommentCount = communityCommentList.size();

        List<Object> masterList = new ArrayList<>();
        masterList.addAll(fandomCommentList);
        masterList.addAll(contentsCommentList);
        masterList.addAll(communityCommentList);

        Collections.sort(masterList, Comparator.comparing(this::getCommentCreatedAt).reversed());
        List<Object>resultArticleList = masterList.stream().limit(2).collect(Collectors.toList());
        List<MemberResponseDto.MemberWrittenDto> memberWrittenCommentDtoList = new ArrayList<>();

        for(int i=0; i<resultArticleList.size();i++){
            if(resultArticleList.get(i) instanceof FandomComment){
                FandomComment targetFandomComment = (FandomComment) resultArticleList.get(i);
                Fandom targetFandom = targetFandomComment.getFandom();
                List<FandomImage> fandomImageList = fandomImageRepository.findFandomImages(targetFandom.getId());
                List<String> imageUrlList = fandomImageList.stream().map(FandomImage::getImage).collect(Collectors.toList());

                MemberResponseDto.MemberWrittenDto fandomArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(targetFandom.getMember().getId())
                        .writerName(targetFandom.getMember().getName())
                        .writerProfile(targetFandom.getMember().getAvatar())
                        .articleType("Fandom")
                        .id(targetFandom.getId())
                        .title(targetFandom.getTitle())
                        .images(imageUrlList)
                        .likeCount(targetFandom.getLikes())
                        .commentCount(targetFandom.getComments())
                        .writtenDate(targetFandom.getCreatedAt())
                        .build();
                memberWrittenCommentDtoList.add(fandomArticle);
            } else if (resultArticleList.get(i) instanceof ContentsComment) {
                ContentsComment targetContentsComment = (ContentsComment) resultArticleList.get(i);
                Contents targetContents = targetContentsComment.getContents();
                List<ContentsImage> contentsImageList = contentImageRepository.findAllByContentsId(targetContentsComment.getId());
                List<String> imageUrlList = contentsImageList.stream().map(ContentsImage::getImage).collect(Collectors.toList());

                MemberResponseDto.MemberWrittenDto contentsArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(targetContents.getMember().getId())
                        .writerName(targetContents.getMember().getName())
                        .writerProfile(targetContents.getMember().getAvatar())
                        .articleType("Contents")
                        .id(targetContents.getId())
                        .title(targetContents.getTitle())
                        .images(imageUrlList)
                        .likeCount(targetContents.getLikes())
                        .commentCount(targetContents.getComments())
                        .writtenDate(targetContents.getCreatedAt())
                        .build();
                memberWrittenCommentDtoList.add(contentsArticle);

            } else if (resultArticleList.get(i) instanceof  CommunityComment) {
                CommunityComment targetCommunityComment = (CommunityComment) resultArticleList.get(i);
                Community tagetCommunity = targetCommunityComment.getCommunity();
                List<CommunityImage> communityImageList = communityImageRepository.findAllByCommunityId(targetCommunityComment.getId());
                List<String> imageUrlList = communityImageList.stream().map(CommunityImage::getImage).collect(Collectors.toList());

                MemberResponseDto.MemberWrittenDto communityArticle = MemberResponseDto.MemberWrittenDto.builder()
                        .writerId(tagetCommunity.getMember().getId())
                        .writerName(tagetCommunity.getMember().getName())
                        .writerProfile(tagetCommunity.getMember().getAvatar())
                        .articleType("Community")
                        .id(tagetCommunity.getId())
                        .title(tagetCommunity.getTitle())
                        .images(imageUrlList)
                        .likeCount(tagetCommunity.getLikes())
                        .commentCount(tagetCommunity.getComments())
                        .writtenDate(tagetCommunity.getCreatedAt())
                        .build();

                memberWrittenCommentDtoList.add(communityArticle);
            }
        }

        Integer commentCount = fandomCommentCount + contentsCommentCount + communityCommentCount;
        return MemberConverter.toMemberWrittenMainDto(commentCount,memberWrittenCommentDtoList);
    }



    private  LocalDateTime getArticleCreatedAt(Object entity) {
        // 각 엔티티의 생성 날짜를 가져오는 메서드
        if (entity instanceof Fandom) {
            return ((Fandom) entity).getCreatedAt();
        } else if (entity instanceof Contents) {
            return ((Contents) entity).getCreatedAt();
        } else if (entity instanceof Community) {
            return ((Community) entity).getCreatedAt();
        }
        return null;
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
}
