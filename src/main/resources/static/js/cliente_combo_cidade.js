var Erp = Erp || {};

Erp.ComboEstado = (function() {
	function ComboEstado() {
		this.combo = $('#estado');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}

	ComboEstado.prototype.init = function() {
		this.combo.on('change', onEstadoChange.bind(this));
	}

	function onEstadoChange() {
		this.emitter.trigger('alterado', this.combo.val());
	}

	return ComboEstado;
}());
// idem ComboEstado
Erp.ComboCidade = (function() {
	function ComboCidade(comboEstado) {
		this.comboEstado = comboEstado;
		this.combo = $('#cidade');
		this.imgLoading = $('.js-img-loading');
		this.inputHiddenCidadeSelected = $('#inputHiddenCidadeSelected');
	}

	ComboCidade.prototype.init = function() {
		reset.call(this);
		this.comboEstado.on('alterado', onEstadoChange.bind(this));
		// se ocorrer um erro de validação para manter o combo cidade preenchido
		var estado = this.comboEstado.combo.val();
		initCidade.call(this, estado);
	}

	function onEstadoChange(event, estado) {
		this.inputHiddenCidadeSelected.val('');
		initCidade.call(this, estado);
	}

	function initCidade(estado){
		if (estado) {
			var resposta = $.ajax({
				url : this.combo.data('url'),
				method : 'GET',
				contentType : 'application/json',
				data : {
					'estado' : estado
				},
				beforeSend : requestInit.bind(this),
				complete : resquestComplete.bind(this)
			});

			resposta.done(onFetchCidadesComplete.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function requestInit() {
		reset.call(this);
		this.imgLoading.show();
	}

	function resquestComplete() {
		this.imgLoading.hide();
	}

	function onFetchCidadesComplete(cidades) {
		var options = [];
		cidades.forEach(function(cidade) {
			options.push('<option value="' + cidade.id + '">' + cidade.nome
					+ '</option>');
		});
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		
		var idCidadeSelected = this.inputHiddenCidadeSelected.val();
		if(idCidadeSelected){
			this.combo.val(idCidadeSelected);
		}
	}

	function reset() {
		this.combo.html('<option value="">Selecione uma cidade</option>');
		this.combo.val('');
		this.combo.attr('disabled', 'disabled');
	}
	return ComboCidade;
}());

$(function(){
	var comboEstado = new Erp.ComboEstado();
	comboEstado.init();

	var comboCidade = new Erp.ComboCidade(comboEstado);
	comboCidade.init();
});