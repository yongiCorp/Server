package com.brandol.converter;

import com.brandol.domain.Member;
import com.brandol.domain.enums.Role;
import com.brandol.domain.enums.UserStatus;
import com.brandol.dto.request.AuthRequestDto;
import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.enums.Provider;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.response.AuthResponseDto;
import com.brandol.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberConverter {

    public static Member toMemberWithEmail(AuthRequestDto.KakaoLoginRequest request) {
        return Member.builder()
                .email(request.getEmail())
                .name(request.getName())
                .role(Role.ROLE_USER)
                .userStatus(UserStatus.ACTIVE)
                .provider(Provider.KAKAO)
                .point(100000)
                .build();
    }

    public static AuthResponseDto.SignUpDto signUpResDto(Long memberId) {
        return AuthResponseDto.SignUpDto.builder()
                .memberId(memberId)
                .signUp(true)
                .build();
    }

    public static MemberResponseDto.MainBannersDto toMainBannersDto(Brand brand){
        return MemberResponseDto.MainBannersDto.builder()
                .brandId(brand.getId())
                .brandBackgroundImage(brand.getBackgroundImage())
                .build();
    }

    public static MemberResponseDto.SubBannersDto toSubBannersDto(Contents contents, List<String>imageUrlList){
        return MemberResponseDto.SubBannersDto.builder()
                .contentId(contents.getId())
                .images(imageUrlList)
                .build();
    }
    public static MemberResponseDto.MemberBrandListDto toMemberBrandListDto(MemberBrandList memberBrandList){
        return MemberResponseDto.MemberBrandListDto.builder()
                .brandId(memberBrandList.getBrand().getId())
                .brandName(memberBrandList.getBrand().getName())
                .profileImage(memberBrandList.getBrand().getProfileImage())
                .brandDescription(memberBrandList.getBrand().getDescription())// 0216 추가
                .sequence(memberBrandList.getSequence())
                .build();
    }

    public static MemberResponseDto.MemberMainDto toMemberMainDto(
            List<MemberResponseDto.MainBannersDto> mainBannersDtoList,
            List<MemberResponseDto.SubBannersDto> subBannersDtoList,
            List<MemberResponseDto.MemberBrandListDto> memberBrandListDtoList){
        return MemberResponseDto.MemberMainDto.builder()
                .mainBannersDtoList(mainBannersDtoList)
                .subBannersDtoList(subBannersDtoList)
                .memberBrandListDtoList(memberBrandListDtoList)
                .build();
    }

    public static MemberResponseDto.MemberWrittenMainDto toMemberWrittenMainDto(
            Integer totalMemberWrittenArticleCount,
            List<MemberResponseDto.MemberWrittenDto> memberWrittenDtoList){
        return MemberResponseDto.MemberWrittenMainDto.builder()
                .totalArticleCount(totalMemberWrittenArticleCount)
                .memberWrittenDtoList(memberWrittenDtoList)
                .build();
    }

    public static MemberResponseDto.MemberAvatarDto toMemberAvatarDto(Member member){

        return MemberResponseDto.MemberAvatarDto.builder()
                .memberId(member.getId())
                .avatar(member.getAvatar())
                .nickname(member.getNickname())
                .build();
    }

    public static AuthResponseDto.UpdateNickname toUpdateNicknameDto(Long memberId, String nickname) {
        return AuthResponseDto.UpdateNickname.builder()
                .memberId(memberId)
                .nickname(nickname)
                .build();
    }

    public static AuthResponseDto.MemberInfo toMemberInfoDto(Member member) {
        return AuthResponseDto.MemberInfo.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .avatar(member.getAvatar())
                .point(member.getPoint())
                .build();
    }
}