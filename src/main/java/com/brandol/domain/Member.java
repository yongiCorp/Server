package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.Gender;
import com.brandol.domain.enums.Provider;
import com.brandol.domain.enums.Role;
import com.brandol.domain.enums.UserStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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

    @Column(length = 45)
    private String name;

    @Column(length = 45, unique = true)
    private String nickname;

    @ColumnDefault("0")
    private int point;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(45)")
    private Gender gender;

    private int age;

    @Column(columnDefinition = "TEXT")
    private String avatar;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean signUp; // 회원가입 완료 여부 - 약관동의, 프로필 등록 완료 여부

    @Enumerated(EnumType.STRING)
    private Provider provider;

    public void setProfile(String nickname, String name, Gender gender, Integer age, String avatar) {
        this.nickname = nickname;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.avatar = avatar;
        this.signUp = true;
    }
}
