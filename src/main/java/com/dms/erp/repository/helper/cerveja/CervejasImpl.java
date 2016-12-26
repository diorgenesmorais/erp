package com.dms.erp.repository.helper.cerveja;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dms.erp.model.Cerveja;
import com.dms.erp.repository.filter.CervejaFilter;

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
	 * necessário para não gerar java.lang.IllegalStateException: No
	 * transactional EntityManager available
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Cerveja> filtrar(CervejaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getSku())) {
				criteria.add(Restrictions.eq("sku", filter.getSku()));
			}

			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
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
		return criteria.list();
	}

}
