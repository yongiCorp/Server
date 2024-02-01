package com.brandol.converter;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Items;
import com.brandol.domain.Member;
import com.brandol.dto.response.SearchResponseDto;

import java.util.List;

public class SearchConverter {
    public static SearchResponseDto.SearchMainBrandDto toSearchMainBrandDto(Brand brand) {
        return SearchResponseDto.SearchMainBrandDto.builder()
                .brandId(brand.getId())
                .brandName(brand.getName())
                .brandDescription(brand.getDescription())
                .brandProfileImage(brand.getProfileImage())
                .build();
    }
    public static SearchResponseDto.SearchMainUserDto toSearchMainUserDto(Member member) {
        return SearchResponseDto.SearchMainUserDto.builder()
                .userId(member.getId())
                .userName(member.getName())
                .userAvatar(member.getAvatar())
                .build();
    }
    public static SearchResponseDto.SearchMainContentsDto toSearchMainContentsDto(
            Contents contents,
            List<String> contentsImages
            ) {
        return SearchResponseDto.SearchMainContentsDto.builder()
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
    public static SearchResponseDto.SearchMainAvatarStoreDto toSearchMainAvatarStoreDto(Items items) {
        return SearchResponseDto.SearchMainAvatarStoreDto.builder()
                .itemId(items.getId())
                .itemsName(items.getName())
                .itemDescription(items.getDescription())
                .brandName(items.getBrand().getName())
                .itemImage(items.getImage())
                .itemPart(String.valueOf(items.getItemPart()))
                .itemPrice(items.getPrice())
                .build();
    }

    public static SearchResponseDto.SearchMainAllDto tosearchMainAllDto(
            List<SearchResponseDto.SearchMainBrandDto> searchMainBrandDtoList,
            List<SearchResponseDto.SearchMainUserDto> searchMainUserDtoList,
            List<SearchResponseDto.SearchMainContentsDto> searchMainContentsDtoList,
            List<SearchResponseDto.SearchMainAvatarStoreDto> searchMainAvatarStoreDtoList){
        return SearchResponseDto.SearchMainAllDto.builder()
                .searchMainBrandDto(searchMainBrandDtoList)
                .searchMainUserDto(searchMainUserDtoList)
                .searchMainContentsDto(searchMainContentsDtoList)
                .searchMainAvatarStoreDto(searchMainAvatarStoreDtoList)
                .build();
    }

}
