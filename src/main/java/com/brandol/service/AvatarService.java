package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.AvatarConverter;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.response.AvatarResponseDto;
import com.brandol.repository.MemberBrandRepository;
import com.brandol.repository.MemberRepository;
import com.brandol.repository.MyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvatarService {

    private final MemberRepository memberRepository;
    private final MemberBrandRepository memberBrandRepository;
    private final MyItemRepository myItemRepository;

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
}
