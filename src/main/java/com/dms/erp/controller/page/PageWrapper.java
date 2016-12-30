package com.dms.erp.controller.page;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	private URIBuilder uriBuild;

	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		// this.uriBuilder = ServletUriComponentsBuilder.fromRequest(request);
		String httpUrl = request.getRequestURL()
				.append(request.getQueryString() != null ? "?" + request.getQueryString() : "").toString()
				.replaceAll("\\+", "%20");

		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
		try {
			this.uriBuild = new URIBuilder(request.getRequestURL()
					.append(request.getQueryString() != null ? "?" + request.getQueryString() : "").toString());
			uriBuild.addParameter("sort", "nome,asc");
			System.out.println(">>>> URI >>>> " + uriBuild.toString());
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro de sintax na URI", e);
		}
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
		return page.getNumber();
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

}
