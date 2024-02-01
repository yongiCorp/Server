package com.brandol.domain.mapping;

import com.brandol.domain.Contents;
import com.brandol.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommunityImage extends BaseEntity {
    // 누락 엔티티 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="community_image_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;
}
