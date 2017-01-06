package com.dms.erp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.erp.model.Cliente;
import com.dms.erp.repository.Clientes;
import com.dms.erp.service.exception.RegisteredAlreadyException;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;

	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> existent = this.clientes.findByCpfOuCnpj(cliente.getCpfOrCnpjWithoutFormatting());
		if (existent.isPresent()) {
			throw new RegisteredAlreadyException("CPF ou CNPJ já existe");
		}
		this.clientes.save(cliente);
	}
}
