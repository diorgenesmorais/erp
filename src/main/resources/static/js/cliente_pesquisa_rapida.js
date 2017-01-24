Erp = Erp || {};

Erp.PesquisaCliente = (function(){
	function PesquisaCliente(){
		this.modal = $('#pesquisaRapidaClientes');
		this.url = this.modal.find('form').attr('action');
		this.inputName = $('#nomeClienteModal');
		this.pesquisaBtn = $('.js-pesquisa-rapida-cliente-btn');
		this.containerTable = $('#container-tabela');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-cliente').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.errorClass = $('.js-mensagem-erro');
	}
	
	PesquisaCliente.prototype.init = function(){
		this.pesquisaBtn.on('click', onConsultaRapida.bind(this));
	}
	
	function onConsultaRapida(event){
		event.preventDefault();

		$.ajax({
			url: this.url,
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.inputName.val()
			},
			success: onPesquisaOk.bind(this),
			error: onErrorPesquisa.bind(this)
		});
	}
	
	function onPesquisaOk(result){
		var html = this.template(result);
		this.containerTable.html(html);
		this.errorClass.addClass('hidden');
	}
	
	function onErrorPesquisa(){
		this.errorClass.removeClass('hidden');
	}
	
	return PesquisaCliente;
}());

$(function(){
	new Erp.PesquisaCliente().init();
});