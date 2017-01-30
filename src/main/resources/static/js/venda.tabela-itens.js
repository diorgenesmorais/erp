Erp.TabelaItens = (function(){
	function TabelaItens(autocomplete){
		this.autocomplete = autocomplete;
		this.tabelaCervejaContainer = $('.js-tabela-cerveja-container');
	}
	
	TabelaItens.prototype.init = function(){
		this.autocomplete.on('item-selecionado', onSelectedItem.bind(this));
	}
	
	function onSelectedItem(event, item){
		var response = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				id: item.id
			}
		});
		response.done(onTabelaCervejaContainer.bind(this));
	}
	
	function onTabelaCervejaContainer(html){
		this.tabelaCervejaContainer.html(html);
		$('.js-tabela-cerveja-qtde-item').on('change', onQtdeItemAlterado.bind(this));
		$('.js-tabela-item').on('dblclick', onDesejaExcluir);
	}
	
	function onQtdeItemAlterado(event){
		var input = $(event.target);
		var qtde = input.val();
		var idCerveja = input.data('id-cerveja');
		
		var response = $.ajax({
			url: 'item/' + idCerveja,
			method: 'PUT',
			data: {
				quantidade: qtde
			}
		});
		
		response.done(onTabelaCervejaContainer.bind(this));
	}
	
	function onDesejaExcluir(event){
		// this = event.currentTarget (this aqui é quem escutou o event: currentTarget = js-tabela-item)
		$(this).toggleClass('solicitando-exclusao');
	}
	
	return TabelaItens;
}());

$(function(){
	var autocomplete = new Erp.Autocomplete();
	autocomplete.init();
	
	new Erp.TabelaItens(autocomplete).init();
});