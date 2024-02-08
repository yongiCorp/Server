package com.brandol.converter;


import com.brandol.domain.Fandom;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.FandomComment;
import com.brandol.dto.request.CommentRequestDto;
import com.brandol.dto.response.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public static CommentResponseDto.CommentDto toFandomCommentDto(FandomComment fandomComment, Member member){
        return CommentResponseDto.CommentDto.builder()
                .commentId(fandomComment.getId())
                .parentId(fandomComment.getParentId())
                .depth(fandomComment.getDepth())
                .writerId(member.getId())
                .writerName(member.getName())
                .writerProfile(member.getAvatar())
                .content(fandomComment.getContent())
                .likeCount(0)
                .writtenDate(fandomComment.getCreatedAt())
                .build();
    }

    public static CommentResponseDto.CommentPackageDto toCommentPackageDto(FandomComment parent, List<FandomComment> childComments){
        CommentResponseDto.CommentDto parentDto = toFandomCommentDto(parent,parent.getWriter());
        List<CommentResponseDto.CommentDto> childDtos = new ArrayList<>();
        for(int i=0; i<childComments.size();i++){
            CommentResponseDto.CommentDto childDto = toFandomCommentDto(childComments.get(i),childComments.get(i).getWriter());
            childDtos.add(childDto);
        }
        return CommentResponseDto.CommentPackageDto.builder()
                .parentDto(parentDto)
                .childDtoList(childDtos)
                .build();
    }
}
