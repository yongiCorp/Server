package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.Gender;
import com.brandol.domain.enums.UserStatus;
import com.brandol.domain.mapping.Community;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 45, unique = true)
    private String nickname;

    @ColumnDefault("0")
    private int point;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(45)")
    private Gender gender;

    @Column(nullable = false)
    private int age;

    @Column(columnDefinition = "TEXT")
    private String avatar;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "member")
    List<Fandom> fandomList;

    @OneToMany(mappedBy = "member")
    List<Contents> contentsList;

    @OneToMany(mappedBy = "member")
    List<Community> communityList;
}
