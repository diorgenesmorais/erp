package com.dms.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.dms.erp.dto.FotoDTO;
import com.dms.erp.storage.FotoStorage;
import com.dms.erp.storage.FotoStorageRunnable;

@RestController
@RequestMapping({ "/fotos" })
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;

	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> result = new DeferredResult<>();

		Thread thred = new Thread(new FotoStorageRunnable(files, result, this.fotoStorage));
		thred.start();

		return result;
	}

	@GetMapping({ "/temp/{nome:.*}" })
	public byte[] recuperarFotoTemporaria(@PathVariable String nome) {
		return this.fotoStorage.recuperarFotoTemp(nome);
	}

	@GetMapping({ "/{nome:.*}" })
	public byte[] recuperarFoto(@PathVariable("nome") String nomeFoto) {
		return this.fotoStorage.recuperarFoto(nomeFoto);
	}
}
