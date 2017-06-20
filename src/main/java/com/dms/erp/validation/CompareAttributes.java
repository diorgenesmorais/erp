package com.dms.erp.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import com.dms.erp.validation.validator.AttributesValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AttributesValidator.class })
public @interface CompareAttributes {

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Os valores informados não são iguais";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String attribute();

	String confirmAttribute();
}
