package com.dms.erp.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dms.erp.model.Cidade;
import com.dms.erp.repository.filter.CidadeFilter;

public interface CidadesQueries {

	public Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable);
}
