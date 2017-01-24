package com.dms.erp.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dms.erp.dto.CervejaDTO;
import com.dms.erp.model.Cerveja;
import com.dms.erp.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);

	public List<CervejaDTO> bySkuOrNome(String skuOuNome);
}
