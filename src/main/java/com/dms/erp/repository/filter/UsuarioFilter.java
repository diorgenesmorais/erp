package com.dms.erp.repository.filter;

import java.util.List;

import com.dms.erp.model.Grupo;

public class UsuarioFilter {

	private String usename;
	private String email;
	private List<Grupo> grupos;

	public String getUsename() {
		return usename;
	}

	public void setUsename(String usename) {
		this.usename = usename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
}
