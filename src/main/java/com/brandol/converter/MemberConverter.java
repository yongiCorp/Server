package com.brandol.converter;

import com.brandol.domain.Member;
import com.brandol.domain.enums.Gender;
import com.brandol.domain.enums.Role;
import com.brandol.domain.enums.UserStatus;
import com.brandol.dto.request.AuthResquestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberConverter {

    public static Member toMember(AuthResquestDto.SignupRequest request) {
        Gender gender = Gender.valueOf(request.getGender());
        return Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .name(request.getName())
                .age(request.getAge())
                .gender(gender)
                .role(Role.ROLE_USER)
                .userStatus(UserStatus.ACTIVE)
                .build();
    }
}
