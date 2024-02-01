package com.brandol.domain.mapping;

import com.brandol.domain.Items;
import com.brandol.domain.Member;
import com.brandol.domain.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="my_item_id")
    private Long id;

    @ColumnDefault("false")
    private Boolean isWearing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="items_id", nullable = false)
    private Items items;

    public void updateIsWearing(Boolean isWearing) {
        this.isWearing = isWearing;
    }
}
