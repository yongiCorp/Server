package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.request.MyItemRequestDto;
import com.brandol.dto.response.ItemResponseDto;
import com.brandol.dto.response.MyItemResponseDto;
import com.brandol.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "내 아바타와 아이템 API")
@RequestMapping("/avatar")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "구매 아이템 조회",description = "내가 구매한 아이템, 아이템의 상세 정보, 아이템의 착용여부 조회")
    @GetMapping("/myitems")
    public ApiResponse<List<MyItemResponseDto.MyItemDto>> getMyItems(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        List<MyItemResponseDto.MyItemDto> myItemList = itemService.getMyItemList(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), myItemList);
    }

    @Operation(summary = "아이템 착용",description = "구매한 아이템 목록 중 사용자가 선택한 아이템을 착용")
    @PatchMapping(value = "/myitems/wear", consumes = "multipart/form-data")
    public ApiResponse<String> wearMyItem(@ModelAttribute MyItemRequestDto.wearMyItemDto request, Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        String wearMyItem = itemService.toWearMyItem(memberId, request);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), wearMyItem);
    }

    @Operation(summary = "아이템 정보 조회",description = "구매한 아이템 목록 중 사용자가 선택한 아이템을 착용")
    @GetMapping("/items/{itemId}")
    public ApiResponse<ItemResponseDto.AvatarStoreBodyListDto> itemInfo(@PathVariable Long itemId, @RequestParam("itemPart")String itemPart) {
        ItemResponseDto.AvatarStoreBodyListDto itemDto = itemService.makeAvatarStoreBodyPage(itemId, itemPart);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), itemDto);
    }
}
