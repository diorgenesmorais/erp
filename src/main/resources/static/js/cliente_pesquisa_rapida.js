Erp = Erp || {};

Erp.PesquisaCliente = (function() {
	function PesquisaCliente() {
		this.modal = $('#pesquisaRapidaClientes');
		this.url = this.modal.find('form').attr('action');
		this.inputName = $('#nomeClienteModal');
		this.pesquisaBtn = $('.js-pesquisa-rapida-cliente-btn');
		this.containerTable = $('#container-tabela');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-cliente').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.errorClass = $('.js-mensagem-erro');
	}

	PesquisaCliente.prototype.init = function() {
		this.pesquisaBtn.on('click', onConsultaRapida.bind(this));
		this.modal.on('shown.bs.modal', onShowModal.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
	}

	function onConsultaRapida(event) {
		event.preventDefault();

		$.ajax({
			url : this.url,
			method : 'GET',
			contentType : 'application/json',
			data : {
				nome : this.inputName.val()
			},
			success : onPesquisaOk.bind(this),
			error : onErrorPesquisa.bind(this)
		});
	}

	function onPesquisaOk(result) {
		var html = this.template(result);
		this.containerTable.html(html);
		this.errorClass.addClass('hidden');

		var tabelaClientePesquisaRapida = new Erp.TabelaClientePesquisaRapida(
				this.modal).init();
	}

	function onErrorPesquisa() {
		this.errorClass.removeClass('hidden');
	}

	function onShowModal() {
		this.inputName.focus();
	}

	function onModalClose(){
		this.inputName.val('');
		this.containerTable.html('');
		this.errorClass.addClass('hidden');
	}
	
	return PesquisaCliente;
}());

Erp.TabelaClientePesquisaRapida = (function() {
	function TabelaClientePesquisaRapida(modal) {
		this.cliente = $('.js-dados-tabela-resultado-cliente');
		this.modalPesquisa = modal;
	}

	TabelaClientePesquisaRapida.prototype.init = function() {
		this.cliente.on('click', onClienteSelecionado.bind(this));
	}

	function onClienteSelecionado(event) {
		var clienteSelecionado = $(event.currentTarget);
		$('#nomeCliente').val(clienteSelecionado.data('nome'));
		$('#idCliente').val(clienteSelecionado.data('id'));
		
		this.modalPesquisa.modal('hide');
	}

	return TabelaClientePesquisaRapida;
}());

$(function() {
	new Erp.PesquisaCliente().init();
});