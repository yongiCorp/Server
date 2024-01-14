package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.ItemPart;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Items extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "items_id")
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Enumerated(EnumType.STRING)
    private ItemPart itemPart;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
}
