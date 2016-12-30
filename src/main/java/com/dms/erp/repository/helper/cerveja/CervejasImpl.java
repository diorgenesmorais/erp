package com.dms.erp.repository.helper.cerveja;

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

import com.dms.erp.model.Cerveja;
import com.dms.erp.repository.filter.CervejaFilter;
import com.dms.erp.repository.pagination.PaginationBuilder;

/**
 * {@code CervejasImpl} é uma implementação de um repositório personalizado.
 * Usando a conversão de nome, motivo de usar {@literal Impl} no nome, ver
 * documentação do método repositoryImplentationPostfix de
 * {@code EnableJpaRepositories}:
 * 
 * @see org.springframework.data.jpa.repository.config.EnableJpaRepositories#repositoryImplementationPostfix()
 * 
 * @author Diorgenes Morais
 *
 */
public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;

	/*
	 * @thows java.lang.IllegalStateException: No transactional EntityManager
	 * available if Transactional#readOnly = false, if you work with Session use
	 * true
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);

		criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();

		addFilter(filter, criteria);
		return new PageImpl<>(criteria.list(), pageable, totalRows(filter));
	}

	private void addFilter(CervejaFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getSku())) {
				criteria.add(Restrictions.eq("sku", filter.getSku()));
			}

			if (!StringUtils.isEmpty(filter.getNome())) {
				// replace " " para %
				criteria.add(Restrictions.ilike("nome", filter.getNome().replaceAll(" ", "%"), MatchMode.ANYWHERE));
			}

			if (filter.getEstilo() != null && filter.getEstilo().getId() != null) {
				criteria.add(Restrictions.eq("estilo", filter.getEstilo()));
			}

			if (filter.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filter.getOrigem()));
			}

			if (filter.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", filter.getSabor()));
			}

			if (filter.getValorDe() != null) {
				// >=
				criteria.add(Restrictions.ge("valor", filter.getValorDe()));
			}

			if (filter.getValorAte() != null) {
				// <=
				criteria.add(Restrictions.le("valor", filter.getValorAte()));
			}
		}
	}

	private long totalRows(CervejaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		addFilter(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (long) criteria.uniqueResult();
	}

}
