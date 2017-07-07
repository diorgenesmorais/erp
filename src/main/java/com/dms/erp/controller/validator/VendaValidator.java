package com.dms.erp.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dms.erp.model.Venda;

@Component
public class VendaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Venda.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*
		 * conforme faz as validações os erros (mensagens) são colocados numa
		 * pilha, ou seja, ordem inversa das validações.
		 */

		ValidationUtils.rejectIfEmpty(errors, "cliente.id", "", "Selecione um cliente na pesquisa rápida");

		Venda venda = (Venda) target;
		validarSeInformouApenasHorarioEntrega(errors, venda);
		validarSeItensVazio(errors, venda);
		validarValorTotal(errors, venda);
	}

	private void validarSeInformouApenasHorarioEntrega(Errors errors, Venda venda) {
		if (venda.getHorarioEntrega() != null && venda.getDataEntrega() == null) {
			errors.rejectValue("dataEntrega", "", "Informe uma data da entrega");
		}
	}

	private void validarSeItensVazio(Errors errors, Venda venda) {
		if (venda.getItens().isEmpty()) {
			errors.reject("", "Informe pelo menos um item");
		}
	}

	private void validarValorTotal(Errors errors, Venda venda) {
		if (venda.getTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.reject("", "O valor total não pode ser negativo");
		}
	}
}
