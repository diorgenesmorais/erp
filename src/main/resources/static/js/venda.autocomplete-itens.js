Erp = Erp || {};

Erp.Autocomplete = (function(){
	function Autocomplete(){
		this.skuOuNomeInput = $('.js-pesquisa-cerveja');
		var htmlTemplate = $('#template-autocomplete-cerveja').html();
		this.template = Handlebars.compile(htmlTemplate);
	}
	
	Autocomplete.prototype.init = function(){
		var options = {
			url: function(skuOuNome){
				return '/erp/cervejas?skuOuNome=' + skuOuNome;
			},
			getValue: 'nome',
			minCharNumber: 3,
			requestDelay: 300,
			ajaxSettings: {
				contentType: 'application/json'
			},
			template: {
				type: 'custom',
				method: function(nome, cerveja){
					cerveja.valorFormatado = Erp.formatarMoeda(cerveja.valor);
					return this.template(cerveja);
				}.bind(this)
			}
		}
		
		this.skuOuNomeInput.easyAutocomplete(options);
	}
	
	return Autocomplete;
}());

$(function(){
	new Erp.Autocomplete().init();
});