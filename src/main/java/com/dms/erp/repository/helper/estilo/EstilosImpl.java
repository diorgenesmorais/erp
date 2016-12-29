package com.dms.erp.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dms.erp.model.Estilo;
import com.dms.erp.repository.filter.EstiloFilter;
import com.dms.erp.repository.pagination.PaginationBuilder;

public class EstilosImpl implements EstilosQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);

		criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();

		addFilter(filter, criteria);

		return new PageImpl<>(criteria.list(), pageable, totalRows(filter));
	}

	private void addFilter(EstiloFilter filter, Criteria criteria) {
		if (!StringUtils.isEmpty(filter.getNome())) {
			criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
		}
	}

	private Long totalRows(EstiloFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		addFilter(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
}
