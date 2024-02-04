package com.brandol.validation.validator;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.service.MemberService;
import com.brandol.validation.annotation.ExistNickname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class NicknameExistValidator implements ConstraintValidator<ExistNickname, String> {

    private final MemberService memberService;

    @Override
    public void initialize(ExistNickname constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = !memberService.existsByNickname(nickname);

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorStatus._MEMBER_NICKNAME_DUPLICATE.toString()).addConstraintViolation();
        }

        return isValid;
    }
}