package com.brandol.converter;

import com.brandol.domain.Fandom;
import com.brandol.domain.Member;
import com.brandol.dto.response.BrandResponseDto;
import com.brandol.dto.response.FandomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FandomConverter {

    public static FandomResponseDto.FandomDto toFandomDto(
            Fandom fandom,
            List<String> fandomImages,
            Member member){
        return FandomResponseDto.FandomDto.builder()
                .writerId(member.getId())
                .writerName(member.getName())
                .writerProfile(member.getAvatar())
                .fandomId(fandom.getId())
                .title(fandom.getTitle())
                .content(fandom.getContent())
                .images(fandomImages)
                .likeCount(fandom.getLikes())
                .commentCount(fandom.getComments())
                .writtenDate(fandom.getCreatedAt())
                .build();
    }
}
