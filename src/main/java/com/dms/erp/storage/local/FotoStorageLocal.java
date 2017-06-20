package com.dms.erp.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.dms.erp.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {
	private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);
	private Path local;
	private Path localTemporario;

	public FotoStorageLocal() {
		this(FileSystems.getDefault().getPath(System.getenv("HOME"), new String[] { ".erpfotos" }));
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		createDirectory();
	}

	public String salvarTemporariamente(MultipartFile[] files) {
		String nameNew = null;
		if ((files != null) && (files.length > 0)) {
			MultipartFile arquivo = files[0];

			File filename = new File(arquivo.getOriginalFilename());
			nameNew = renameFile(filename.getName());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString()
						+ FileSystems.getDefault().getSeparator() + nameNew));
			} catch (IOException e) {
				throw new RuntimeException("Erro ao salvar foto na pasta temp", e);
			}
		}
		return nameNew;
	}

	public byte[] recuperarFotoTemp(String nomeFoto) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nomeFoto));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar foto temporaria");
		}
	}

	public byte[] recuperarFoto(String nomeFoto) {
		try {
			return Files.readAllBytes(this.local.resolve(nomeFoto));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar foto salva");
		}
	}

	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível mover a foto", e);
		}
		try {
			Thumbnails.of(new String[] { this.local.resolve(foto).toString() }).size(40, 68)
					.toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível criar o thumbnails", e);
		}
	}

	private void createDirectory() {
		try {
			Files.createDirectories(this.local, new FileAttribute[0]);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), new String[] { "temp" });
			Files.createDirectories(this.localTemporario, new FileAttribute[0]);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas criadas...");
				LOGGER.debug("Pasta default: " + this.local.toAbsolutePath());
				LOGGER.debug("Pasta temp: " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar diretório para salvar imagens", e);
		}
	}

	private String renameFile(String filename) {
		String nameNew = UUID.randomUUID().toString() + "_" + filename;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Nome original: %s, novo nome: %s", new Object[] { filename, nameNew }));
		}
		return nameNew;
	}
}