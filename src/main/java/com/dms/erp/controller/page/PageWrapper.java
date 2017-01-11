package com.dms.erp.controller.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	private final int MAX_PAGE = 5;
	private List<PageItem> items;
	private int start;
	private int currentNumber;

	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		String httpUrl = request.getRequestURL()
				.append(request.getQueryString() != null ? "?" + request.getQueryString() : "").toString()
				.replaceAll("\\+", "%20");

		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
		config();
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
		return currentNumber;
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

	public String urlOfPage(int current) {
		
		System.out.println("chamou urlOfPage >>>>>>>>> " + current);
		/*
		 * Métodos {@code UriComponentsBuilder#build(true)#encode()} resolvem a
		 * falta de decodificação e encodificação da página por causa de valores
		 * monetários.
		 */
		return uriBuilder.replaceQueryParam("page", current).build(true).encode().toUriString();
	}

	public String urlOrderBy(String property) {
		UriComponentsBuilder uriComponentBuilder = UriComponentsBuilder.fromUriString(uriBuilder.build().toString());

		String sortValue = String.format("%s,%s", property, reverseOrder(property));

		String uri = uriComponentBuilder.replaceQueryParam("sort", sortValue).build(true).encode().toUriString();

		return uri;
	}

	/*
	 * Returns null if not ordered.
	 */
	private Order getOrder(String property) {
		return page.getSort() != null ? page.getSort().getOrderFor(property) : null;
	}

	public boolean isOrder(String property) {
		return getOrder(property) != null;
	}

	/*
	 * Returns true if ascending, returns false if not ordered, that is, being
	 * false does not guarantee that it is not downward.
	 */
	public boolean isAscending(String property) {
		Order order = getOrder(property);
		return order != null && order.isAscending();
	}

	public String reverseOrder(String property) {
		String direction = "asc";

		if (isAscending(property)) {
			direction = "desc";
		}

		return direction;
	}

	public class PageItem {
		private int index;

		public PageItem(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

	}

	public List<PageItem> getItems() {
		return this.items;
	}

	private void config() {
		this.currentNumber = page.getNumber();
		this.start = 1;
		int status = (this.currentNumber + 1) - MAX_PAGE;
		
			if (status >= 0) {
				this.start += status;
			}
			System.out.println("Status " + status + " Start: " + this.start);
		this.items = new ArrayList<>();
		int size, remaining;
		remaining = this.page.getTotalPages() - this.currentNumber;
		
		if (remaining >= MAX_PAGE) {
			size = MAX_PAGE;
		} else {
			size = remaining;
		}

		for (int i = 0; i < size; i++) {
			this.items.add(new PageItem(this.start + i));
		}
	}

}
