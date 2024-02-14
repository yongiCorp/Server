package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.HistoryType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="point_history_id")
    private Long id;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    private HistoryType history;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
