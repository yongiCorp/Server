package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.request.MyItemRequestDto;
import com.brandol.dto.response.AvatarResponseDto;
import com.brandol.dto.response.MyItemResponseDto;
import com.brandol.service.AvatarService;
import com.brandol.dto.response.ItemResponseDto;
import com.brandol.service.ItemService;
import com.brandol.service.MemberService;
import com.brandol.service.PointHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "내 아바타와 아이템 API")
@RequestMapping("/avatar")
public class ItemController {

    private final ItemService itemService;
    private final AvatarService avatarService;
    private final PointHistoryService pointHistoryService;

    @Operation(summary = "아바타 구매 아이템 조회",description = "내가 구매한 아이템, 아이템의 상세 정보, 아이템의 착용여부를 조회합니다. 응답의 image: 착용 전 아이템(보유아이템 목록) 이미지, wearingImage: 아바타 착용 아이템 이미지")
    @GetMapping("/myitems")
    public ApiResponse<List<MyItemResponseDto.MyItemDto>> getMyItems(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        List<MyItemResponseDto.MyItemDto> myItemList = itemService.getMyItemList(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), myItemList);
    }

    @Operation(summary = "아이템 착용한 아바타 저장",description = "구매한 아이템 목록 중 사용자가 선택한 아이템을 착용 및 아바타 이미지 저장")
    @PatchMapping(value = "/myitems/wear", consumes = "multipart/form-data")
    public ApiResponse<String> wearMyItem(@ModelAttribute MyItemRequestDto.wearItemsDto request, Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        String wearMyItem = itemService.toWearMyItem(memberId, request);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), wearMyItem);
    }

    @Operation(summary = "다른 회원의 브랜드 리스트 조회")
    @GetMapping("/{memberId}/brandsList")
    public ApiResponse<List<AvatarResponseDto.OtherMemberBrandListDto>> getOtherMemberBrandList(@PathVariable("memberId") Long memberId) {
        List<AvatarResponseDto.OtherMemberBrandListDto> otherMemberBrandListDto = avatarService.getOtherMemberBrandList(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), otherMemberBrandListDto);
    }

    @Operation(summary = "타 회원의 아바타가 착용한 아이템 조회",description ="유저 프로필 클릭 혹은 유저 검색 시 해당 아바타의 착용 아이템 조회")
    @GetMapping("/{memberId}/items")
    public ApiResponse<List<AvatarResponseDto.MemberAvatarItemDto>> getMemberAvatarItem(@PathVariable("memberId") Long memberId) {
        List<AvatarResponseDto.MemberAvatarItemDto> memberAvatarItemListDto = avatarService.getMemberAvatarItem(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), memberAvatarItemListDto);
    }

    @Operation(summary = "다른 회원이 작성한 글 조회", description = "다른 회원이 작성한 커뮤니티(자유 게시판, 피드백 게시판)의 글 목록 조회합니다. 10개 씩 조회해오는 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @GetMapping("/{memberId}/community")
    @Parameters({
            @Parameter(name = "memberId", description = "선택된 회원의 memberId 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다."),
    })
    public ApiResponse<List<AvatarResponseDto.OtherMemberCommunityDto>> getOtherMemberCommunity(@PathVariable("memberId") Long memberId, @RequestParam(name = "page") Integer page) {
        List<AvatarResponseDto.OtherMemberCommunityDto> otherMemberCommunityListDto = avatarService.getOtherMemberCommunity(memberId, page);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), otherMemberCommunityListDto);
    }

    @Operation(summary = "아이템 정보 조회",description = "검색 페이지에서 특정 아이템 클릭 시, 피그마 기준 5페이지 d의 최상단에서 해당 아이템 정보 조회 ")
    @GetMapping("/items/{itemId}")
    public ApiResponse<ItemResponseDto.AvatarStoreBodyListDto> itemInfo(@PathVariable Long itemId, @RequestParam("itemPart")String itemPart) {
        ItemResponseDto.AvatarStoreBodyListDto itemDto = itemService.makeAvatarStoreBodyPage(itemId, itemPart);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), itemDto);
    }

    @Operation(summary = "내 아바타 이미지 조회")
    @GetMapping("/myAvatar")
    public ApiResponse<String> getMyAvatar(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        String myAvatar = avatarService.getMyAvatar(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), myAvatar);
    }

    @Operation(summary = "아이템 구매")
    @PostMapping("/items/{itemId}")
    public ApiResponse<?> purchaseItem(@PathVariable("itemId") Long itemId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        MyItem myItem = itemService.purchaseItem(memberId, itemId);
        pointHistoryService.makePurchaseHistory(myItem);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(),null);
    }
}
