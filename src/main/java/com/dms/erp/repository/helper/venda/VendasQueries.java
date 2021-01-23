package com.dms.erp.repository.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dms.erp.model.Venda;
import com.dms.erp.repository.filter.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter filter, Pageable pageable);
}
