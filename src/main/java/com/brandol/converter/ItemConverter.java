package com.brandol.converter;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.response.ItemResponseDto;
import com.brandol.dto.response.MyItemResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.brandol.domain.Items;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemConverter {
    public static MyItemResponseDto.MyItemDto toMyItemResDTO(MyItem myItem) {

        return MyItemResponseDto.MyItemDto.builder()
                .myItemId(myItem.getId())
                .isWearing(myItem.getIsWearing())
                .itemId(myItem.getItems().getId())
                .itemName(myItem.getItems().getName())
                .part(myItem.getItems().getItemPart().toString())
                .description(myItem.getItems().getDescription())
                .image(myItem.getItems().getImage())
                .brandId(myItem.getItems().getBrand().getId())
                .brandName(myItem.getItems().getBrand().getName())
                .price(myItem.getItems().getPrice())
                .createdAt(myItem.getCreatedAt())
                .build();
    }

    public static ItemResponseDto.AvatarStoreBodyDto toItemResDTO(Items item) {

        return ItemResponseDto.AvatarStoreBodyDto.builder()
                .itemId(item.getId())
                .itemsName(item.getName())
                .itemDescription(item.getDescription())
                .itemImage(item.getImage())
                .itemPart(item.getItemPart().toString())
                .itemPrice(item.getPrice())
                .brandName(item.getBrand().getName())
                .build();
    }

    public static ItemResponseDto.AvatarStoreBodyListDto toAvatarStoreBodyAllDto(
            List<ItemResponseDto.AvatarStoreBodyDto> AvatarStoreBodyDtoList){
        return ItemResponseDto.AvatarStoreBodyListDto.builder()
                .AvatarStoreBodyDto(AvatarStoreBodyDtoList)
                .build();
    }

}
