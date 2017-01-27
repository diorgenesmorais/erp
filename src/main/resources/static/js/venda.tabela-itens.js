Erp.TabelaItens = (function(){
	function TabelaItens(autocomplete){
		this.autocomplete = autocomplete;
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
		response.done(function(data){
			console.log(data);
		});
	}
	
	return TabelaItens;
}());

$(function(){
	var autocomplete = new Erp.Autocomplete();
	autocomplete.init();
	
	new Erp.TabelaItens(autocomplete).init();
});