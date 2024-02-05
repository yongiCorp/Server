package com.brandol.converter;


import com.brandol.domain.Fandom;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.FandomComment;
import com.brandol.dto.request.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentConverter {

    public static FandomComment toFandomCommentEntity(CommentRequestDto.addComment dto, Member member, Fandom fandom){

        return FandomComment.builder()
                .parentId(null)
                .depth(0L)
                .content(dto.getContent())
                .isDeleted(false)
                .writer(member)
                .fandom(fandom)
                .build();
    }
}
