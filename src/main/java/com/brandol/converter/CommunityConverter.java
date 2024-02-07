package com.brandol.converter;

import com.brandol.domain.Contents;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.Community;
import com.brandol.dto.response.CommunityResponseDto;
import com.brandol.dto.response.ContentsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommunityConverter {

    public static CommunityResponseDto.CommunityDto toContentsDto(
            Community community,
            List<String> communityImages,
            Member member){
        return CommunityResponseDto.CommunityDto.builder()
                .writerId(member.getId())
                .writerName(member.getName())
                .writerProfile(member.getAvatar())
                .communityId(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .images(communityImages)
                .likeCount(community.getLikes())
                .commentCount(community.getComments())
                .writtenDate(community.getCreatedAt())
                .build();
    }
}
