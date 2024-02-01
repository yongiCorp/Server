package com.brandol.domain.mapping;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.MemberListStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberBrandList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_brand_list_id")
    private Long id;

    private Long sequence;

    @Enumerated(EnumType.STRING)
    private MemberListStatus memberListStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    public void changeMemberListStatus(MemberListStatus memberListStatus){
        this.memberListStatus = memberListStatus;
    }
}
