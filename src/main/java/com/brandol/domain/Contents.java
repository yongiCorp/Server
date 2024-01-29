package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.ContentsType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Contents extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContentsType contentsType;

    @Column(columnDefinition = "TEXT")
    private String file;

    private String title; // 2024-01-19 누락 필드 추가

    private String content; // 2024-01-19 누락 필드 추가

    @ColumnDefault("0") // 2024-01-29 누락 필드 추가
    private int likes;

    @ColumnDefault("0") // 2024-01-29 누락 필드 추가
    private int comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id",nullable = false)
    private Brand brand;

    public void addFile(String fileURL){
        this.file = fileURL;
    }
}
