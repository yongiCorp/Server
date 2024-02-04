package com.brandol.validation.annotation;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import com.brandol.validation.validator.BrandExistValidator;
@Documented
@Constraint(validatedBy = BrandExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistBrand {
        String message() default "해당 브랜드가 존재하지 않습니다.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}


