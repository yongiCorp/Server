package com.brandol.converter;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.response.MyItemResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
}
