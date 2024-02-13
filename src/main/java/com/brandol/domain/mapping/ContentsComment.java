package com.brandol.domain.mapping;

import com.brandol.domain.Contents;
import com.brandol.domain.Member;
import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.LikeStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContentsComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_comment_id")
    private Long id;

    private Long parentId;
    private Long depth;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("false")
    private Boolean isDeleted;
    //작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer", nullable = false)
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", nullable = false)
    private Contents contents;

    @ColumnDefault("0")
    private int likes;
    public void addParentId(Long parentId){
        this.parentId = parentId;
    }
    public void updateLikes(int number){this.likes = number;}
}
