var Erp = Erp || {};

Erp.HandleFoto = (function(){
	
	function HandleFoto(){
		this.inputNomeFoto = $('input[name=foto]');
		this.inputNomeContentType = $('input[name=contentType]');
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);

		this.uploadFoto = $('#upload-drop');
		this.containerFotoCerveja = $('.js-container-foto-cerveja');
	}
	
	HandleFoto.prototype.init = function(){
		var settings = {
			type: 'json',
			filelimit: 1,
			allow: '*.(jpg|jpeg|png)',
			action: this.containerFotoCerveja.data('url-fotos'),
			complete: onUploadComplete.bind(this)
		}
		
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadFoto, settings);
		
		if(this.inputNomeFoto.val()){
			onUploadComplete.call(this, { nome: this.inputNomeFoto.val(), 
				contentType: this.inputNomeContentType.val()});
		}
	}
	
	function onUploadComplete(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputNomeContentType.val(resposta.contentType);
		
		var htmlFotoCerveja = this.template({nomeFoto: resposta.nome});
		this.uploadFoto.addClass('hidden');
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		$('.js-remove-foto').on('click', onRemoveFoto.bind(this));
	}
	
	function onRemoveFoto(){
		$('.js-foto-cerveja').remove();
		this.uploadFoto.removeClass('hidden');
		this.inputNomeFoto.val('');
		this.inputNomeContentType.val('');
	}
	
	return HandleFoto;
}());

$(function(){
	var uploadFoto = new Erp.HandleFoto();
	uploadFoto.init();
});















/*$(function() {
	var settings = {
		type: 'json',
		filelimit: 1,
		allow: '*.(jpg|jpeg|png)',
		action: '/erp/fotos',
		complete: function(resposta){
			var inputNomeFoto = $('input[name=foto]');
			var inputNomeContentType = $('input[name=contentType]');
			var htmlFotoCervejaTemplate = $('#foto-cerveja').html();
			var template = Handlebars.compile(htmlFotoCervejaTemplate);
			var htmlFotoCerveja = template({nomeFoto: resposta.nome});
			
			var uploadFoto = $('#upload-drop');
			var containerFotoCerveja = $('.js-container-foto-cerveja');
			
			inputNomeFoto.val(resposta.nome);
			inputNomeContentType.val(resposta.contentType);
			uploadFoto.addClass('hidden');
			containerFotoCerveja.append(htmlFotoCerveja);
			
			$('.js-remove-foto').on('click', function(){
				$('.js-foto-cerveja').remove();
				uploadFoto.removeClass('hidden');
				inputNomeFoto.val('');
				inputNomeContentType.val('');
			});
			var url = $('#formCerveja').attr('action');
			console.log(url);
		}
	};
			
	UIkit.uploadSelect($('#upload-select'), settings);
	UIkit.uploadDrop($('#upload-drop'), settings);
});*/