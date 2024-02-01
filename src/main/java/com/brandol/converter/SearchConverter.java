package com.brandol.converter;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Items;
import com.brandol.domain.Member;
import com.brandol.dto.response.SearchMainResponseDto;

import java.util.List;

public class SearchConverter {
    public static SearchMainResponseDto.SearchMainBrandDto toSearchMainBrandDto(Brand brand) {
        return SearchMainResponseDto.SearchMainBrandDto.builder()
                .brandId(brand.getId())
                .brandName(brand.getName())
                .brandDescription(brand.getDescription())
                .brandProfileImage(brand.getProfileImage())
                .build();
    }
    public static SearchMainResponseDto.SearchMainUserDto toSearchMainUserDto(Member member) {
        return SearchMainResponseDto.SearchMainUserDto.builder()
                .userId(member.getId())
                .userName(member.getName())
                .userAvatar(member.getAvatar())
                .build();
    }
    public static SearchMainResponseDto.SearchMainContentsDto toSearchMainContentsDto(
            Contents contents,
            List<String> contentsImages
            ) {
        return SearchMainResponseDto.SearchMainContentsDto.builder()
                .contentsId(contents.getId())
                .contentsTitle(contents.getTitle())
                .content(contents.getContent())
                .images(contentsImages)
                .likeCount(contents.getLikes())
                .commentCount(contents.getComments())
                .writerId(contents.getMember().getId())
                .writerName(contents.getMember().getName())
                .writerProfile(contents.getMember().getAvatar())
                .createdDate(contents.getCreatedAt())
                .build();
    }
    public static SearchMainResponseDto.SearchMainAvatarStoreDto toSearchMainAvatarStoreDto(Items items) {
        return SearchMainResponseDto.SearchMainAvatarStoreDto.builder()
                .itemId(items.getId())
                .itemsName(items.getName())
                .itemDescription(items.getDescription())
                .brandName(items.getBrand().getName())
                .itemImage(items.getImage())
                .itemPart(String.valueOf(items.getItemPart()))
                .itemPrice(items.getPrice())
                .build();
    }

    public static SearchMainResponseDto.SearchMainAllDto tosearchMainAllDto(
            List<SearchMainResponseDto.SearchMainBrandDto> searchMainBrandDtoList,
            List<SearchMainResponseDto.SearchMainUserDto> searchMainUserDtoList,
            List<SearchMainResponseDto.SearchMainContentsDto> searchMainContentsDtoList,
            List<SearchMainResponseDto.SearchMainAvatarStoreDto> searchMainAvatarStoreDtoList){
        return SearchMainResponseDto.SearchMainAllDto.builder()
                .searchMainBrandDto(searchMainBrandDtoList)
                .searchMainUserDto(searchMainUserDtoList)
                .searchMainContentsDto(searchMainContentsDtoList)
                .searchMainAvatarStoreDto(searchMainAvatarStoreDtoList)
                .build();
    }

}
