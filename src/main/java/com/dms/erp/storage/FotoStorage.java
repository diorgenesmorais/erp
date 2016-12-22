package com.dms.erp.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public abstract String salvarTemporariamente(MultipartFile[] files);

	public abstract byte[] recuperarFotoTemp(String nomeFoto);

	public abstract byte[] recuperarFoto(String nomeFoto);

	public abstract void salvar(String nomeFoto);
}
