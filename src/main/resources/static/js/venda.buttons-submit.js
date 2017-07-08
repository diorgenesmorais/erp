Erp = Erp || {}

Erp.ButtonSubmit = (function(){
	
	function ButtonSubmit(){
		this.submitBtn = $('.js-submit-btn');
		this.formulario = $('.js-form-principal');
	}
	
	ButtonSubmit.prototype.init = function(){
		this.submitBtn.on('click', onSubmit.bind(this));
	}
	
	function onSubmit(event){
		event.preventDefault();

		var botaoClicado = $(event.target);
		var acao = botaoClicado.data('acao');
		
		var acaoInput = $('<input>');
		acaoInput.attr('name', acao);
		
		this.formulario.append(acaoInput);
		this.formulario.submit();
	}
	
	return ButtonSubmit;
}());

$(function(){
	var buttonSubmit = new Erp.ButtonSubmit();
	buttonSubmit.init();
});