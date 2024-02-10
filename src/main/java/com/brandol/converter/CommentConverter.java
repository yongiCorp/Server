package com.brandol.converter;


import com.brandol.domain.Fandom;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.CommunityComment;
import com.brandol.domain.mapping.ContentsComment;
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
                .likeCount(fandomComment.getLikes())
                .writtenDate(fandomComment.getCreatedAt())
                .build();
    }

    public static CommentResponseDto.CommentDto toContentsCommentDto(ContentsComment contentsComment, Member member){
        return CommentResponseDto.CommentDto.builder()
                .commentId(contentsComment.getId())
                .parentId(contentsComment.getParentId())
                .depth(contentsComment.getDepth())
                .writerId(member.getId())
                .writerName(member.getName())
                .writerProfile(member.getAvatar())
                .content(contentsComment.getContent())
                .likeCount(contentsComment.getLikes())
                .writtenDate(contentsComment.getCreatedAt())
                .build();
    }

    public static CommentResponseDto.CommentPackageDto toFandomCommentPackageDto(FandomComment parent, List<FandomComment> childComments){
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

    public static CommentResponseDto.CommentPackageDto toContentsCommentPackageDto(ContentsComment parent, List<ContentsComment> childComments){
        CommentResponseDto.CommentDto parentDto = toContentsCommentDto(parent,parent.getWriter());
        List<CommentResponseDto.CommentDto> childDtos = new ArrayList<>();
        for(int i=0; i<childComments.size();i++){
            CommentResponseDto.CommentDto childDto = toContentsCommentDto(childComments.get(i),childComments.get(i).getWriter());
            childDtos.add(childDto);
        }
        return CommentResponseDto.CommentPackageDto.builder()
                .parentDto(parentDto)
                .childDtoList(childDtos)
                .build();
    }

    public static CommentResponseDto.CommentDto toCommunityCommentDto(CommunityComment communityComment, Member member){
        return CommentResponseDto.CommentDto.builder()
                .commentId(communityComment.getId())
                .parentId(communityComment.getParentId())
                .depth(communityComment.getDepth())
                .writerId(member.getId())
                .writerName(member.getName())
                .writerProfile(member.getAvatar())
                .content(communityComment.getContent())
                .likeCount(communityComment.getLikes())
                .writtenDate(communityComment.getCreatedAt())
                .build();
    }

    public static CommentResponseDto.CommentPackageDto toCommunityCommentPackageDto(CommunityComment parent, List<CommunityComment> childComments){
        CommentResponseDto.CommentDto parentDto = toCommunityCommentDto(parent,parent.getWriter());
        List<CommentResponseDto.CommentDto> childDtos = new ArrayList<>();
        for(int i=0; i<childComments.size();i++){
            CommentResponseDto.CommentDto childDto = toCommunityCommentDto(childComments.get(i),childComments.get(i).getWriter());
            childDtos.add(childDto);
        }
        return CommentResponseDto.CommentPackageDto.builder()
                .parentDto(parentDto)
                .childDtoList(childDtos)
                .build();
    }
}
