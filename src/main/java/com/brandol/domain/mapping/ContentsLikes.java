package com.brandol.domain.mapping;

import com.brandol.domain.Contents;
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
public class ContentsLikes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contents_likes_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="contents_id", nullable = false)
    private Contents contents;

    public void changeLikeStatus(LikeStatus likeStatus){
        this.likeStatus = likeStatus;
    }
}
