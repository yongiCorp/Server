package com.brandol.domain.mapping;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.CommunityType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Community extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CommunityType communityType;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    private int likes;
    @ColumnDefault("0")
    private int comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    public void updateComments(int number){this.comments=number;}

    public void updateLikes(int number){this.likes = number;}
}
