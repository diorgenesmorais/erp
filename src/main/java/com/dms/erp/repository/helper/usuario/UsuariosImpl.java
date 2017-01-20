package com.dms.erp.repository.helper.usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dms.erp.model.Grupo;
import com.dms.erp.model.Usuario;
import com.dms.erp.model.UsuarioGrupo;
import com.dms.erp.repository.filter.UsuarioFilter;
import com.dms.erp.service.exception.IdNullException;

public class UsuariosImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Optional<Usuario> byEmailAndActive(String email) {
		return manager.createQuery("from Usuario where lower(email) = lower(:email) and active = true", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> filtrar(UsuarioFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		addFilter(filter, criteria);

		return criteria.list();
	}

	private void addFilter(UsuarioFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getUsename())) {
				criteria.add(Restrictions.ilike("usename", filter.getUsename(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filter.getEmail())) {
				criteria.add(Restrictions.ilike("email", filter.getEmail(), MatchMode.START));
			}

			/**
			 * A necessidade é gerar uma query abaixo.
			 * 
			 * <pre>
			 * select distinct * from usuario u 
			 * left outer join usuario_grupo ug on u.id=ug.usuario_id 
			 * left outer join grupo g on ug.grupo_id=g.id 
			 * where (u.id in(select usuario_id from usuario_grupo where grupo_id=1)
			 * and u.id in(select usuario_id from usuario_grupo where grupo_id=2));
			 * </pre>
			 */
			criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			if (filter.getGrupos() != null && !filter.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for (Long grupo_id : filter.getGrupos().stream().mapToLong(Grupo::getId).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.id", grupo_id));
					dc.setProjection(Projections.property("id.usuario"));

					subqueries.add(Subqueries.propertyIn("id", dc));
				}
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}

	@Override
	public void changeActiveByIds(boolean active, Long[] ids) {
		try {
			List<Long> lista = Arrays.asList(ids);

			Query query = manager.createNativeQuery("update usuario set active=:active where id in(:ids)");
			query.setParameter("active", active);
			query.setParameter("ids", lista);
			query.executeUpdate();
		} catch (NullPointerException e) {
			throw new IdNullException("Precisa informar pelo menos um ID", e);
		}
	}

}
