var Erp = Erp || {};

Erp.EstiloCadastroRapido = (function(){
	
	function EstiloCadastroRapido(){
		this.modal = $('#modalCadastroRapidoEstilo');
		this.btnSalvar = this.modal.find('.js-modal-estilo-cadastro-salvar-btn');
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.inputNomeEstilo = $('#nomeEstilo');
		this.containerMessageError = $('.js-message-cadastro-rapido-estilo');
	}
	
	EstiloCadastroRapido.prototype.init = function(){
		this.form.on('submit', function(event){event.preventDefault()});
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
		this.btnSalvar.on('click', onBtnSalvar.bind(this));
	}
	
	function onModalShow(){
		this.inputNomeEstilo.focus();
	}
	
	function onModalClose(){
		this.inputNomeEstilo.val('');
		this.containerMessageError.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onBtnSalvar(){
		var nomeEstilo = this.inputNomeEstilo.val().trim();
		$.ajax({
			url : this.url,
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({ nome : nomeEstilo }),
			error : onErroSalvandoestilo.bind(this),
			success : onEstiloSalvar.bind(this)
		});
	}
	
	function onErroSalvandoestilo(obj){
		// objeto Json, representação de DetailedError.java
		var messageError = obj.responseJSON.responseText;
		this.containerMessageError.removeClass('hidden');
		this.containerMessageError.html('<span>' + messageError + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}
	
	function onEstiloSalvar(estilo){
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value=' + estilo.id + '>' + estilo.nome + '</option>');
		comboEstilo.val(estilo.id);
		this.modal.modal('hide');
	}
	
	return EstiloCadastroRapido;
	
}());

$(function(){
	var estiloCadastroRapido = new Erp.EstiloCadastroRapido();
	estiloCadastroRapido.init();
});