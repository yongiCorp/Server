package com.brandol.validation.validator;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.domain.Brand;
import com.brandol.repository.BrandRepository;
import com.brandol.service.BrandService;
import com.brandol.validation.annotation.ExistBrand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

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

        boolean result = brandService.isExistBrand(id);


        if (!result){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus._NOT_EXIST_BRAND.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
