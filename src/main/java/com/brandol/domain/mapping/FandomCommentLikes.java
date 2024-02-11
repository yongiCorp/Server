package com.brandol.domain.mapping;

import com.brandol.domain.Member;
import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.LikeStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FandomCommentLikes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fandom_comment_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fandom_comment_id",nullable = false)
    private FandomComment fandomComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    public void changeLikeStatus(LikeStatus likeStatus){this.likeStatus = likeStatus;}
}
