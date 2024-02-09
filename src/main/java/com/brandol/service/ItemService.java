package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.aws.AmazonS3Manager;
import com.brandol.converter.AvatarConverter;
import com.brandol.domain.Member;
import com.brandol.converter.ItemConverter;
import com.brandol.domain.Items;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.request.MyItemRequestDto;
import com.brandol.dto.response.ItemResponseDto;
import com.brandol.dto.response.MyItemResponseDto;
import com.brandol.repository.ItemsRepository;
import com.brandol.repository.MemberRepository;
import com.brandol.repository.MyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final MyItemRepository myItemRepository;
    private final ItemsRepository itemsRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Manager s3Manager;

    public List<MyItemResponseDto.MyItemDto> getMyItemList(Long memberId) {

        List<MyItem> myItemList = myItemRepository.findALlByMemberId(memberId);
        List<MyItemResponseDto.MyItemDto> myItemDtoList = new ArrayList<>();

        for (MyItem myItem : myItemList) {
            MyItemResponseDto.MyItemDto myItemDto = AvatarConverter.toMyItemResDTO(myItem);
            myItemDtoList.add(myItemDto);
        }
        return myItemDtoList;
    }

    @Transactional
    public String toWearMyItem(Long memberId, MyItemRequestDto.wearMyItemDto request) {
        List<MyItem> currentMyItemList = myItemRepository.findALlByMemberIdAndIsWearing(memberId, true);
        List<MyItem> wearingMyItemList = request.getWearingMyItemIdList().stream()
                .map(myItemId -> myItemRepository.findById(myItemId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MY_ITEM)))
                .collect(Collectors.toList());

        currentMyItemList.stream()
                .filter(currentMyItem -> !wearingMyItemList.contains(currentMyItem))// 현재 착용중인 아이템이 wearingMyItemList에 없으면
                .forEach(currentMyItem -> currentMyItem.updateIsWearing(false)); // isWearing은 false
        wearingMyItemList.forEach(myItem -> {myItem.updateIsWearing(true);});

        if (request.getAvatarImage() != null && !request.getAvatarImage().isEmpty()) {
            // 기존 아바타 파일 S3에서 삭제
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
            String existingMemberAvatar = member.getAvatar();
            if (existingMemberAvatar != null && !existingMemberAvatar.isEmpty()) {
                String existingAvatarFileName = s3Manager.getAvatarKeyName(existingMemberAvatar);
                s3Manager.deleteFile(existingAvatarFileName);
            }
            // 새로운 아바타 파일 업로드
            String avatar = request.getAvatarImage().getOriginalFilename();
            String avatarUUID = s3Manager.generateAvatarKeyName(avatar);
            String avatarURL = s3Manager.uploadFile(avatarUUID, request.getAvatarImage());
            // 해당 회원의 아바타 수정
            member.updateAvatar(avatarURL);
            return "아바타 저장 완료";
        } else {
            throw new ErrorHandler(ErrorStatus._FILE_AVATAR_INVALID);
        }
    }
    public ItemResponseDto.AvatarStoreBodyListDto makeAvatarStoreBodyPage(Long itemId, String itemPart){





        if(itemPart.equals("전체")){
            // 전체 아바타 스토어 리스트
            List<Items> total_item_List = itemsRepository.finditemByIdandRandom(itemId);

            List<ItemResponseDto.AvatarStoreBodyDto> AvatarStore_Body_Total_DtoList = new ArrayList<>();
            for(int i=0; i< total_item_List.size();i++){
                ItemResponseDto.AvatarStoreBodyDto dto = ItemConverter.toItemResDTO(total_item_List.get(i));
                AvatarStore_Body_Total_DtoList.add(dto);
            }

            return ItemConverter.toAvatarStoreBodyAllDto(AvatarStore_Body_Total_DtoList);
        }







        // 종류별 아바타 스토어 리스트
        List<Items> itempart_item_List = itemsRepository.finditemPartByRandom(itemPart);


        List<ItemResponseDto.AvatarStoreBodyDto> AvatarStore_Body_DtoList = new ArrayList<>();
        for(int i=0; i< itempart_item_List.size();i++){
            ItemResponseDto.AvatarStoreBodyDto dto = ItemConverter.toItemResDTO(itempart_item_List.get(i));
            AvatarStore_Body_DtoList.add(dto);
        }


        return ItemConverter.toAvatarStoreBodyAllDto(AvatarStore_Body_DtoList);


    }
}
