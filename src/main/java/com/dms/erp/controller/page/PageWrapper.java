package com.dms.erp.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(request);
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
}
