package com.dms.erp.repository.pagination;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Sets up a Criteria object with pagination.
 * 
 * @author Diorgenes Morais
 * @version 1.0.0
 */
public class PaginationBuilder {

	private Criteria criteria;
	private Pageable pageable;

	public PaginationBuilder(Criteria criteria, Pageable pageable) {
		this.criteria = criteria;
		this.pageable = pageable;
		this.setLimit();
	}

	private void setLimit() {
		int pageNumber = this.pageable.getPageNumber();
		int maxResult = this.pageable.getPageSize();
		int firstResult = pageNumber * maxResult;
		this.criteria.setFirstResult(firstResult);
		this.criteria.setMaxResults(maxResult);
	}

	/**
	 * Build with ordination.
	 * 
	 * @return {@code Criteria}
	 */
	public PaginationBuilder withOrdination() {
		Sort sort = this.pageable.getSort();
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			this.criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
		return this;
	}

	public Criteria builder() {
		return this.criteria;
	}
}
