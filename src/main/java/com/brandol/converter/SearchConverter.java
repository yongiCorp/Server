package com.brandol.converter;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Items;
import com.brandol.domain.Member;
import com.brandol.dto.response.SearchResponseDto;

import java.util.List;

public class SearchConverter {

    //검색 메인 페이지
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

    //검색 더보기 페이지 - 브랜드
    public static SearchResponseDto.SearchDetailBrandDto toSearchDetailBrandDto(Brand brand, int brandFans) {
        return SearchResponseDto.SearchDetailBrandDto.builder()
                .brandId(brand.getId())
                .brandName(brand.getName())
                .brandDescription(brand.getDescription())
                .brandFans(brandFans)
                .brandProfileImage(brand.getProfileImage())
                .brandBackgroundImage(brand.getBackgroundImage())
                .build();
    }

    public static SearchResponseDto.SearchDetailBrandAllDto tosearchDetailBrandAllDto(
            List<SearchResponseDto.SearchDetailBrandDto> searchDetailBrandDtoList){
        return SearchResponseDto.SearchDetailBrandAllDto.builder()
                .searchDetailBrandDto(searchDetailBrandDtoList)
                .build();
    }

    //검색 더보기 페이지 - 유저
    public static SearchResponseDto.SearchDetailUserDto toSearchDetailUserDto(Member member) {
        return SearchResponseDto.SearchDetailUserDto.builder()
                .userId(member.getId())
                .userName(member.getName())
                .userAvatar(member.getAvatar())
                .build();
    }

    public static SearchResponseDto.SearchDetailUserAllDto tosearchDetailUserAllDto(
            List<SearchResponseDto.SearchDetailUserDto> searchDetailUserDtoList){
        return SearchResponseDto.SearchDetailUserAllDto.builder()
                .searchDetailUserDto(searchDetailUserDtoList)
                .build();
    }

    //검색 더보기 페이지 - 컨텐츠
    public static SearchResponseDto.SearchDetailContentsDto toSearchDetailContentsDto(
            Contents contents,
            List<String> contentsImages) {
        return SearchResponseDto.SearchDetailContentsDto.builder()
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
    public static SearchResponseDto.SearchDetailContentsAllDto tosearchDetailContentsAllDto(
            List<SearchResponseDto.SearchDetailContentsDto> searchDetailContentsDtoList){
        return SearchResponseDto.SearchDetailContentsAllDto.builder()
                .searchDetailContentsDto(searchDetailContentsDtoList)
                .build();
    }

    //검색 더보기 페이지 - 아바타 스토어 헤더
    public static SearchResponseDto.SearchDetailAvatarStoreHeaderDto toSearchDetailAvatarStoreHeaderDto(Member member) {
        return SearchResponseDto.SearchDetailAvatarStoreHeaderDto.builder()
                .memberId(member.getId())
                .memberAvatar(member.getAvatar())
                .memberPoints(member.getPoint())
                .build();
    }

    //검색 더보기 페이지 - 아바타 스토어 바디
    public static SearchResponseDto.SearchDetailAvatarStoreBodyDto toSearchDetailAvatarStoreBodyDto(Items items) {
        return SearchResponseDto.SearchDetailAvatarStoreBodyDto.builder()
                .itemId(items.getId())
                .itemsName(items.getName())
                .itemDescription(items.getDescription())
                .brandName(items.getBrand().getName())
                .itemImage(items.getImage())
                .itemPart(String.valueOf(items.getItemPart()))
                .itemPrice(items.getPrice())
                .build();
    }

    public static SearchResponseDto.Search_Detail_AvatarStore_Body_All_Dto tosearchDetailAvatarStoreBodyAllDto(
            List<SearchResponseDto.SearchDetailAvatarStoreBodyDto> searchDetailAvatarStoreBodyDtoList){
        return SearchResponseDto.Search_Detail_AvatarStore_Body_All_Dto.builder()
                .searchDetailAvatarStoreBodyDto(searchDetailAvatarStoreBodyDtoList)
                .build();
    }
}
