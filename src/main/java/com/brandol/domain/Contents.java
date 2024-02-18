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
    private String file; //이미지 외의 첩부파일

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id",nullable = false)
    private Brand brand;

    @ColumnDefault("0")
    private int likes;

    @ColumnDefault("0")
    private int comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void addFile(String fileURL){
        this.file = fileURL;
    }
    public void updateComments(int number){this.comments=number;}
    public void updateLikes(int number){this.likes = number;}
}
