package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.ItemConverter;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.response.MyItemResponseDto;
import com.brandol.repository.BrandRepository;
import com.brandol.repository.ItemsRepository;
import com.brandol.repository.MemberRepository;
import com.brandol.repository.MyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final MyItemRepository myItemRepository;
    public List<MyItemResponseDto.MyItemDto> getMyItemList(Long memberId) {
        //Member member = memberRepository.findById(userId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        List<MyItem> myItemList = myItemRepository.findALlByMemberId(memberId);
        /*if (myItemList.isEmpty()) {
            throw new ErrorHandler();
        }*/
        List<MyItemResponseDto.MyItemDto> myItemDtoList = new ArrayList<>();

        for (MyItem myItem : myItemList) {
            MyItemResponseDto.MyItemDto myItemDto = ItemConverter.toMyItemResDTO(myItem);
            myItemDtoList.add(myItemDto);
        }
        return myItemDtoList;
    }
}
