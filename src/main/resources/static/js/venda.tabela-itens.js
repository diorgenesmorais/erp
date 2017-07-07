Erp.TabelaItens = (function(){
	function TabelaItens(autocomplete){
		this.autocomplete = autocomplete;
		this.tabelaCervejaContainer = $('.js-tabela-cerveja-container');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	TabelaItens.prototype.init = function(){
		this.autocomplete.on('item-selecionado', onSelectedItem.bind(this));
		
		bindQuantidade.call(this);
		bindTabelaItens.call(this);
	}
	
	TabelaItens.prototype.valorTotal = function(){
		return this.tabelaCervejaContainer.data('valor');
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
	
	function bindQuantidade(){
		var qtdeItem = $('.js-tabela-cerveja-qtde-item');
		qtdeItem.on('change', onQtdeItemAlterado.bind(this));
		qtdeItem.maskMoney({precision: 0, thousands: ''});
	}
	
	function bindTabelaItens(){
		var tabelaItens = $('.js-tabela-item');
		tabelaItens.on('dblclick', onDesejaExcluir);
		$('.js-exclusao-item-btn').on('click', onExclusaoItem.bind(this));
		return tabelaItens;
	}
	
	function onTabelaCervejaContainer(html){
		this.tabelaCervejaContainer.html(html);
		
		bindQuantidade.call(this);
		
		bindTabelaItens.call(this);
		
		var tabelaItens = bindTabelaItens.call(this);
		this.emitter.trigger('tabela-itens-atualizada', tabelaItens.data('valor-total'));
	}
	
	function onQtdeItemAlterado(event){
		var input = $(event.target);
		var qtde = input.val() <= 0 ? input.val(1) : input.val();
		
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
	
	function onExclusaoItem(event){
		var idCerveja = $(event.target).data('id-cerveja');
		var response = $.ajax({
			url: 'item/' + idCerveja,
			method: 'DELETE'
		});
		
		response.done(onTabelaCervejaContainer.bind(this));
	}
	
	return TabelaItens;
}());

Erp.Venda = (function(){
	function Venda(tabelaItens){
		this.tabelaItens = tabelaItens;
		this.valorTotalBoxContainer = $('.js-valor-total-box');
		this.valorTotalBox = $('#valor-total');
		this.valorFreteInput = $('#valorFrete');
		this.valorDescontoInput = $('#valorDesconto');
		
		this.valorTotalItens = this.tabelaItens.valorTotal();
		this.valorFrete = this.valorFreteInput.data('valor');
		this.valorDesconto = this.valorDescontoInput.data('valor');
	}
	
	Venda.prototype.init = function(){
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtual.bind(this));
		this.valorFreteInput.on('keyup', onValorFreteAlterado.bind(this));
		this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));
		
		onValoresAlterados.call(this);
	}
	
	function onTabelaItensAtual(event, valorTotalItens){
		this.valorTotalItens = Number(valorTotalItens || 0);
		onValoresAlterados.call(this);
	}
	
	function onValorFreteAlterado(event){
		this.valorFrete = Erp.undoFormatting($(event.target).val());
		onValoresAlterados.call(this);
	}
	
	function onValorDescontoAlterado(event){
		this.valorDesconto = Erp.undoFormatting($(event.target).val());
		onValoresAlterados.call(this);
	}
	
	function onValoresAlterados(){
		var valorTotal = numeral(this.valorTotalItens).value() + numeral(this.valorFrete).value() - numeral(this.valorDesconto).value();
		this.valorTotalBox.html(Erp.formatarMoeda(valorTotal));
		this.valorTotalBoxContainer.toggleClass('negative', valorTotal < 0);

	}
	
	return Venda;
}());

$(function(){
	var autocomplete = new Erp.Autocomplete();
	autocomplete.init();
	
	var tabelaItens = new Erp.TabelaItens(autocomplete);
	tabelaItens.init();
	
	var venda = new Erp.Venda(tabelaItens);
	venda.init();
});