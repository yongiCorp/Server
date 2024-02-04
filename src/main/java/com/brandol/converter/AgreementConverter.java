package com.brandol.converter;

import com.brandol.domain.Member;
import com.brandol.domain.Term;
import com.brandol.domain.mapping.Agreement;
import com.brandol.dto.response.AuthResponseDto;

public class AgreementConverter {
    public static Agreement toAgreement(Member member, Term term) {
        return Agreement.builder()
                .member(member)
                .term(term)
                .build();
    }

    public static AuthResponseDto.AgreeTermsDto toAgreementResDto (Member member) {
        return AuthResponseDto.AgreeTermsDto.builder()
                .memberId(member.getId())
                .signUp(false)
                .build();
    }
}
