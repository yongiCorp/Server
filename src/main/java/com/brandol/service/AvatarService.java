package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.AvatarConverter;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.Community;
import com.brandol.domain.mapping.CommunityImage;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.response.AvatarResponseDto;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvatarService {

    private final MemberRepository memberRepository;
    private final MemberBrandRepository memberBrandRepository;
    private final MyItemRepository myItemRepository;
    private final CommunityRepository communityRepository;
    private final CommunityImageRepository communityImageRepository;

    public List<AvatarResponseDto.OtherMemberBrandListDto> getOtherMemberBrandList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        List<MemberBrandList> otherMemberBrandLists = memberBrandRepository.findAllSubscribedByMemberId(memberId);
        return AvatarConverter.toOtherMemberBrandListDto(otherMemberBrandLists);
    }

    public List<AvatarResponseDto.MemberAvatarItemDto> getMemberAvatarItem(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        List<MyItem> memberAvatarItems = myItemRepository.findALlByMemberIdAndIsWearing(memberId, true);
        return AvatarConverter.toMemberAvatarItemListDto(memberAvatarItems);
    }

    public List<AvatarResponseDto.OtherMemberCommunityDto> getOtherMemberCommunity(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        Page<Community> otherMemberCommunityList = communityRepository.findByMember(member, PageRequest.of(page, 10,  Sort.by(Sort.Direction.DESC, "createdAt")));
        List<CommunityImage> communityImages = communityImageRepository.findByCommunityIdIn(
                otherMemberCommunityList.stream()
                        .map(Community::getId)
                        .collect(Collectors.toList()));
        return AvatarConverter.toOtherMemberCommunityListDto(otherMemberCommunityList, communityImages);
    }

    public String getMyAvatar(Long memberId) {
        Member member= memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        return member.getAvatar();
    }
}