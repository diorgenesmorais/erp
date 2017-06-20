package com.dms.erp.service;

import com.dms.erp.repository.Usuarios;

public enum StatusUsuario {

	ATIVAR {
		@Override
		public void executar(Usuarios usuarios, Long[] ids) {
			usuarios.changeActiveByIds(true, ids);
		}
	},
	DESATIVAR {
		@Override
		public void executar(Usuarios usuarios, Long[] ids) {
			usuarios.changeActiveByIds(false, ids);
		}
	};

	public abstract void executar(Usuarios usuarios, Long[] ids);
}
