package com.dms.erp.repository.helper.cidade;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dms.erp.model.Cidade;
import com.dms.erp.repository.filter.CidadeFilter;
import com.dms.erp.repository.pagination.PaginationBuilder;

public class CidadesImpl implements CidadesQueries {

	@Autowired
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);

		criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();

		addFilter(filter, criteria);

		return new PageImpl<>(criteria.list(), pageable, totalRows(filter));
	}

	private Long totalRows(CidadeFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		addFilter(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void addFilter(CidadeFilter filter, Criteria criteria) {
		if (!StringUtils.isEmpty(filter.getNome())) {
			criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
		}
		if (filter.getEstado() != null) {
			criteria.add(Restrictions.eq("estado", filter.getEstado()));
		}
	}

}
