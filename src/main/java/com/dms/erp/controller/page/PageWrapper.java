package com.dms.erp.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

import com.dms.useful.pagination.PagesLimitControl;

/**
 * Wrapper a {@code Page} object
 * 
 * @author Diorgenes Morais
 *
 * @param <T>
 *            the type of the model
 * @version 1.0.2
 */
public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	private PagesLimitControl pageControl;

	/**
	 * Configures the default constructor with
	 * {@link PagesLimitControl#LIMIT_MAX_PAGE}
	 * 
	 * @param page
	 *            a {@link Pageable} object
	 * @param request
	 *            a {@link HttpServletRequest} object
	 */
	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this(page, request, PagesLimitControl.LIMIT_MAX_PAGE);
	}

	/**
	 * Pattern constructor
	 * 
	 * @param page
	 *            a {@link Pageable} object
	 * @param request
	 *            a {@link HttpServletRequest} object
	 * @param numberOfMaximumPages
	 *            limit the maximum number of pages
	 */
	public PageWrapper(Page<T> page, HttpServletRequest request, int numberOfMaximumPages) {
		this.page = page;
		String httpUrl = request.getRequestURL()
				.append(request.getQueryString() != null ? "?" + request.getQueryString() : "").toString()
				.replaceAll("\\+", "%20");

		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);

		this.pageControl = new PagesLimitControl(this.page.getNumber(), this.page.getTotalPages(),
				numberOfMaximumPages);
	}

	public List<T> getContent() {
		return page.getContent();
	}

	public boolean isEmpty() {
		return page.getContent().isEmpty();
	}

	public boolean isExists() {
		return page.hasContent();
	}

	public int getCurrentNumber() {
		return this.page.getNumber();
	}

	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public int getTotalPages() {
		return page.getTotalPages();
	}

	/**
	 * Delimita a primeira página.
	 * 
	 * @return número da primeira página.
	 */
	public int getNumberInitial() {
		return pageControl.getFirst();
	}

	/**
	 * Delimita a última página
	 * 
	 * @return a última página (no limite)
	 */
	public int getLimitPages() {
		return pageControl.getLast();
	}

	public String urlOfPage(int current) {
		/*
		 * Métodos {@code UriComponentsBuilder#build(true)#encode()} resolvem a
		 * falta de decodificação e encodificação da página por causa de valores
		 * monetários.
		 */
		return uriBuilder.replaceQueryParam("page", current).build(true).encode().toUriString();
	}

	public String urlOrderBy(String property) {
		UriComponentsBuilder uriComponentBuilder = UriComponentsBuilder.fromUriString(uriBuilder.build().toString());

		String sortValue = String.format("%s,%s", property, this.reverseOrder(property));

		String uri = uriComponentBuilder.replaceQueryParam("sort", sortValue).build(true).encode().toUriString();

		return uri;
	}

	/**
	 * Returns null if not ordered.
	 * 
	 * @return Order obtain a {@code Order} object or null
	 */
	private Order getOrder(String property) {
		return page.getSort() != null ? page.getSort().getOrderFor(property) : null;
	}

	public boolean isOrder(String property) {
		return getOrder(property) != null;
	}

	/**
	 * @return Returns true if ascending, returns false if not ordered, that is,
	 *         being false does not guarantee that it is not downward.
	 */
	public boolean isAscending(String property) {
		Order order = getOrder(property);
		return order != null && order.isAscending();
	}

	public String reverseOrder(String property) {
		return this.isAscending(property) ? "desc" : "asc";
	}

}
