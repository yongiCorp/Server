package com.brandol.converter;

import com.brandol.domain.Contents;
import com.brandol.domain.Member;
import com.brandol.dto.response.ContentsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContentsConverter {

    public static ContentsResponseDto.ContentsDto toContentsDto(
            Contents contents,
            List<String> contentsImages,
            Member member){
        return ContentsResponseDto.ContentsDto.builder()
                .writerId(member.getId())
                .writerName(member.getName())
                .writerProfile(member.getAvatar())
                .contentsId(contents.getId())
                .title(contents.getTitle())
                .content(contents.getContent())
                .images(contentsImages)
                .file(contents.getFile())
                .likeCount(contents.getLikes())
                .commentCount(contents.getComments())
                .writtenDate(contents.getCreatedAt())
                .build();
    }
}
