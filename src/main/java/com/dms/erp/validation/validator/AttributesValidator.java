package com.dms.erp.validation.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import com.dms.erp.validation.CompareAttributes;

/**
 * {@code AttributesValidator} is a attributes validator.
 * 
 * @author Diorgenes Morais
 * @version 1.0.0
 */
public class AttributesValidator implements ConstraintValidator<CompareAttributes, Object> {

	private String attribute;
	private String confirmAttribute;

	@Override
	public void initialize(CompareAttributes constraintAnnotation) {
		this.attribute = constraintAnnotation.attribute();
		this.confirmAttribute = constraintAnnotation.confirmAttribute();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		boolean valid = false;
		try {
			Object attributValue = BeanUtils.getProperty(object, this.attribute);
			Object confirmAttributeValue = BeanUtils.getProperty(object, this.confirmAttribute);

			valid = this.isAttributesNull(attributValue, confirmAttributeValue)
					|| this.isAttributesEquals(attributValue, confirmAttributeValue);

		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Erro ao tentar recuperar atributos " + this.getClass().getName(), e);
		}

		if (!valid) {
			context.disableDefaultConstraintViolation(); /* para não duplicar a mensagem, já que são 2 atributos */
			String messageTemplate = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(messageTemplate);
			violationBuilder.addPropertyNode(this.confirmAttribute).addConstraintViolation();
		}

		return valid;
	}

	private boolean isAttributesEquals(Object attributValue, Object confirmAttributeValue) {
		return attributValue != null && attributValue.equals(confirmAttributeValue);
	}

	private boolean isAttributesNull(Object attributValue, Object confirmAttributeValue) {
		return attributValue == null && confirmAttributeValue == null;
	}

}
