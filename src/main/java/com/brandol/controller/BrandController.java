package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.Brand;
import com.brandol.dto.request.BrandRequestDto;
import com.brandol.dto.response.BrandResponseDto;
import com.brandol.dto.response.MemberResponseDto;
import com.brandol.service.BrandService;
import com.brandol.service.MemberMissionService;
import com.brandol.service.MemberService;
import com.brandol.validation.annotation.ExistBrand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "브랜드 관련 API", description = "피그마 기준 페이지 2의 브랜드 상단 부분 조회, 하단 부분의 팬덤, 컨텐츠, 커뮤니티 조회")
public class BrandController {

    private final BrandService brandService;
    private final MemberService memberService;
    private final MemberMissionService memberMissionService;


    @Operation(summary = "브랜드 생성",description ="브랜돌 서비스에 브랜드를 신규 등록하는 함수",hidden = false ) // 테스트 해야함
    @PostMapping(value = "/brands/new",consumes = "multipart/form-data") // 브랜드 신규 등록 함수
    private ApiResponse<Brand> addNewBrand(@ModelAttribute BrandRequestDto.addBrand request){
        Brand brand = brandService.createBrand(request);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), brand);
    }

    @Operation(summary = "브랜드 공통 헤더 조회",description ="브랜드 프로필,배경이미지, 구독자 수등 브랜드 상세정보 헤더를 조회" )
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/header")
    public ApiResponse<BrandResponseDto.BrandHeaderDto> showBrandHeader(@PathVariable("brandId")Long brandId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        BrandResponseDto.BrandHeaderDto brandHeaderDto = brandService.makeBrandCommonHeader(brandId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), brandHeaderDto);
    }
    @Operation(summary = "브랜드 팬덤 조회",description ="브랜드 팬덤에 종속된 브랜드 컬처, 브랜드 공지사항 최신 2건을 조회" )
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/fandom")
    public ApiResponse<BrandResponseDto.BrandFandomDto> showBrandFandom(@ExistBrand @PathVariable("brandId")Long brandId) {
        BrandResponseDto.BrandFandomDto brandFandomDto = brandService.makeBrandFandomBody(brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), brandFandomDto);
    }

    @Operation(summary = "브랜드 콘텐츠 조회",description = "브랜드 콘텐츠에 종속된 브랜드 이벤트, 브랜드 카드뉴스, 브랜드 비디오 최신 2건을 조회")
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/contents")
    public ApiResponse<BrandResponseDto.BrandContentsDto> showBrandContents(@ExistBrand @PathVariable("brandId")Long brandId){
        BrandResponseDto.BrandContentsDto brandContentsDto = brandService.makeBrandContentsBody(brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), brandContentsDto);
    }

    @Operation(summary = "브랜드 커뮤니티 조회",description = "브랜드 콘텐츠에 종속된 브랜드 자유게시판, 피드백 게시판 최신 2건을 조회")
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/community")
    public ApiResponse<BrandResponseDto.BrandCommunityDto> showBrandCommunity(@ExistBrand @PathVariable("brandId")Long brandId){
        BrandResponseDto.BrandCommunityDto brandCommunityDto = brandService.makeCommunityBody(brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), brandCommunityDto);
    }

    @Operation(summary = "멤버 작성 글 조회",description ="2페이지 d 진입시 호출하는 API" )
    @GetMapping("/brands/{brandId}/my-written/articles")
    public ApiResponse<MemberResponseDto.MemberWrittenMainDto> memberWrittenArticle(@PathVariable("brandId")Long brandId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        MemberResponseDto.MemberWrittenMainDto dto = memberService.makeBrandMemberWrittenPage(memberId,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), dto);
    }

    @Operation(summary = "멤버 작성 댓글 조회",description ="2페이지 d 진입시 호출하는 API" )
    @GetMapping("/brands/{brandId}/my-written/comments")
    public ApiResponse<MemberResponseDto.MemberWrittenMainDto> memberWrittenComment(@PathVariable("brandId")Long brandId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        MemberResponseDto.MemberWrittenMainDto dto = memberService.makeBrandMemberWrittenCommentPage(memberId,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), dto);
    }

    @Operation(summary = "브랜드 커뮤니티 게시글 생성",description = "브랜드 콘텐츠에 종속된 브랜드 자유게시판, 피드백 게시판에 게시글 생성")
    @Parameter(name = "brandId",description = "생성 대상 브랜드의 ID")
    @PostMapping(value = "/brands/{brandId}/community/new",consumes = "multipart/form-data")
    public ApiResponse<String> crateBrandCommunity(@ModelAttribute BrandRequestDto.addCommunity communityDto,
                                                   @PathVariable("brandId")Long brandId,
                                                   Authentication authentication
                                                   ){
        Long memberId = Long.parseLong(authentication.getName());
        Long communityId = brandService.createCommunity(communityDto,brandId,memberId);
        memberMissionService.checkCommunityMission(memberId, brandId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(),"article-id: "+communityId);
    }
}
