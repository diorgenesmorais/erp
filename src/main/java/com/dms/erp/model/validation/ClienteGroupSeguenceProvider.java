package com.dms.erp.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.dms.erp.model.Cliente;

public class ClienteGroupSeguenceProvider implements DefaultGroupSequenceProvider<Cliente> {

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {
		List<Class<?>> groups = new ArrayList<>();
		groups.add(Cliente.class);
		if (isPeopleSelected(cliente)) {
			groups.add(cliente.getTipoPessoa().getGroup());
		}
		return groups;
	}

	private boolean isPeopleSelected(Cliente cliente) {
		return cliente != null && cliente.getTipoPessoa() != null;
	}

}
