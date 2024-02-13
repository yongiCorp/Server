package com.brandol.converter;
import com.brandol.domain.mapping.Community;
import com.brandol.domain.mapping.CommunityImage;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.response.AvatarResponseDto;
import com.brandol.dto.response.MyItemResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvatarConverter {
    public static MyItemResponseDto.MyItemDto toMyItemResDTO(MyItem myItem) {

        return MyItemResponseDto.MyItemDto.builder()
                .myItemId(myItem.getId())
                .isWearing(myItem.getIsWearing())
                .itemId(myItem.getItems().getId())
                .itemName(myItem.getItems().getName())
                .part(myItem.getItems().getItemPart().toString())
                .description(myItem.getItems().getDescription())
                .image(myItem.getItems().getImage())
                .wearingImage(myItem.getItems().getWearingImage())
                .brandId(myItem.getItems().getBrand().getId())
                .brandName(myItem.getItems().getBrand().getName())
                .price(myItem.getItems().getPrice())
                .createdAt(myItem.getCreatedAt())
                .build();
    }

    public static List<AvatarResponseDto.OtherMemberBrandListDto> toOtherMemberBrandListDto(List<MemberBrandList> otherMemberBrandLists) {
        return otherMemberBrandLists.stream()
                .map(memberBrandList -> AvatarResponseDto.OtherMemberBrandListDto.builder()
                        .brandId(memberBrandList.getBrand().getId())
                        .brandName(memberBrandList.getBrand().getName())
                        .description(memberBrandList.getBrand().getDescription())
                        .profileImage(memberBrandList.getBrand().getProfileImage())
                        .sequence(memberBrandList.getSequence())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<AvatarResponseDto.MemberAvatarItemDto> toMemberAvatarItemListDto(List<MyItem> memberAvatarItems) {
        return memberAvatarItems.stream()
                .map(memberAvatarItem -> AvatarResponseDto.MemberAvatarItemDto.builder()
                        .myItemId(memberAvatarItem.getId())
                        .itemId(memberAvatarItem.getItems().getId())
                        .brandId(memberAvatarItem.getItems().getBrand().getId())
                        .brandName(memberAvatarItem.getItems().getBrand().getName())
                        .itemName(memberAvatarItem.getItems().getName())
                        .isWearing(memberAvatarItem.getIsWearing())
                        .part(memberAvatarItem.getItems().getItemPart().toString())
                        .description(memberAvatarItem.getItems().getDescription())
                        .image(memberAvatarItem.getItems().getImage())
                        .price(memberAvatarItem.getItems().getPrice())
                        .createdAt(memberAvatarItem.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<AvatarResponseDto.OtherMemberCommunityDto> toOtherMemberCommunityListDto(/*List<Community>*/ Page<Community> otherMemberCommunityList, List<CommunityImage> communityImages) {
        return otherMemberCommunityList.stream()
                .map(community -> AvatarResponseDto.OtherMemberCommunityDto.builder()
                        .writerId(community.getMember().getId())
                        .writerName(community.getMember().getName())
                        .writerProfile(community.getMember().getAvatar())
                        .articleType(community.getCommunityType().toString())
                        .id(community.getId())
                        .title(community.getTitle())
                        .content(community.getContent())
                        .images(communityImages.stream()
                                .filter(communityImage -> communityImage.getCommunity().getId().equals(community.getId()))
                                .map(CommunityImage::getImage)
                                .collect(Collectors.toList()))
                        .likeCount(community.getLikes())
                        .commentCount(community.getComments())
                        .writtenDate(community.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
