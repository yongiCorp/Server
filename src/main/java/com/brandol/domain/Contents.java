package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.ContentsType;
import lombok.*;

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
    private String file; //이미지 외의 첩부파일

    private String title; // 2014-01-19 누락 필드 추가

    private String content; // 2014-01-19 누락 필드 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id",nullable = false)
    private Brand brand;

    private int likes;

    private int comments;

    public void addFile(String fileURL){
        this.file = fileURL;
    }
}
