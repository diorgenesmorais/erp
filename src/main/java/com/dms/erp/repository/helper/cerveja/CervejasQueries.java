package com.dms.erp.repository.helper.cerveja;

import java.util.List;

import com.dms.erp.model.Cerveja;
import com.dms.erp.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public List<Cerveja> filtrar(CervejaFilter filter);
}
