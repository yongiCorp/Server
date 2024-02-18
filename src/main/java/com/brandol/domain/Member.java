package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import com.brandol.domain.enums.Gender;
import com.brandol.domain.enums.Provider;
import com.brandol.domain.enums.Role;
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

    @Column(length = 45)
    private String name;

    @Column(length = 45, unique = true)
    private String nickname;

    @ColumnDefault("1000")
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


    @OneToMany(mappedBy = "member")
    List<Fandom> fandomList;

    @OneToMany(mappedBy = "member")
    List<Contents> contentsList;

    @OneToMany(mappedBy = "member")
    List<Community> communityList;

    public void setProfile(String nickname, Gender gender, Integer age, String avatar) {
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.avatar = avatar;
        this.signUp = true;
    }

    public void updateAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void inactivate(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void updatePoint(int num) { this.point += num;}

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

}