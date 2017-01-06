package com.dms.erp.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dms.erp.model.Cliente;
import com.dms.erp.repository.filter.ClienteFilter;

public interface ClientesQueries {

	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable);
}
