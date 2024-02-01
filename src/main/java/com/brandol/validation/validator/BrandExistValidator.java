package com.brandol.validation.validator;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.service.BrandService;
import com.brandol.validation.annotation.ExistBrand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class BrandExistValidator implements ConstraintValidator<ExistBrand, Long> {


    private final BrandService brandService;

    @Override
    public void initialize(ExistBrand constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {

        boolean result = brandService.isExistBrand(id); // 해당 브랜드 id가 존재하면 true

        if (!result){ //해당 브랜드 아이디가 존재하지 않는 경우
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus._NOT_EXIST_BRAND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}


