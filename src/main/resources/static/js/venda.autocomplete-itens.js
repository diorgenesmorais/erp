Erp = Erp || {};

Erp.Autocomplete = (function() {
	function Autocomplete() {
		this.skuOuNomeInput = $('.js-pesquisa-cerveja');
		var htmlTemplate = $('#template-autocomplete-cerveja').html();
		this.template = Handlebars.compile(htmlTemplate);
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}

	Autocomplete.prototype.init = function() {
		var options = {
			url : function(skuOuNome) {
				return '/erp/cervejas?skuOuNome=' + skuOuNome;
			},
			getValue : 'nome',
			minCharNumber : 3,
			requestDelay : 300,
			ajaxSettings : {
				contentType : 'application/json'
			},
			template : {
				type : 'custom',
				method : onTemplate.bind(this)
			},
			list : {
				onChooseEvent : onSelectionItem.bind(this)
			}
		}

		this.skuOuNomeInput.easyAutocomplete(options);
	}

	function onTemplate(nome, cerveja) {
		cerveja.valorFormatado = Erp.formatarMoeda(cerveja.valor);
		return this.template(cerveja);
	}

	function onSelectionItem() {
		this.emitter.trigger('item-selecionado', this.skuOuNomeInput.getSelectedItemData());
	}

	return Autocomplete;
}());