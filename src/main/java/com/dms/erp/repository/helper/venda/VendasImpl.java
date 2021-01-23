package com.dms.erp.repository.helper.venda;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.dms.erp.model.Venda;
import com.dms.erp.repository.filter.VendaFilter;
import com.dms.useful.pagination.PaginationBuilder;

public class VendasImpl implements VendasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<Venda> filtrar(VendaFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);

		criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();
		addFiltro(criteria, filter);

		return new PageImpl<>(criteria.list(), pageable, totalRows(filter));
	}

	private long totalRows(VendaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		addFiltro(criteria, filter);
		criteria.setProjection(Projections.rowCount());
		return (long) criteria.uniqueResult();
	}

	private void addFiltro(Criteria criteria, VendaFilter filter) {
		// para ordenação pelo nome
		criteria.createAlias("cliente", "c");

		if (filter != null) {
			if(filter.getId() != null) {
				criteria.add(Restrictions.eq("id", filter.getId()));
			}
			
			if (filter.getStatusVenda() != null) {
				criteria.add(Restrictions.eqOrIsNull("status", filter.getStatusVenda()));
			}
			
			if(filter.getDataInicial() != null) {
				LocalDateTime desde = LocalDateTime.of(filter.getDataInicial(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataCriacao", desde));
			}
			
			/*if(filter.getValorTotalMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", filter.getValorTotalMinimo()));
			}
			
			if(filter.getValorTotalMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", filter.getValorTotalMaximo()));
			}*/
		

		if (filter != null) {
			criteria.add(Restrictions.eqOrIsNull("status", filter.getStatusVenda()));
		}
	}
}
