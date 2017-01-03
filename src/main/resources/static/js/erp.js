var Erp = Erp || {};

Erp.Formatter = (function() {

	function Formatter() {
		this.configMask = {
			decimal : ',',
			thousands : '.',
			allowZero : true
		};
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}

	Formatter.prototype.enable = function() {
		this.decimal.maskMoney(this.configMask);

		this.configMask.precision = 0;

		this.plain.maskMoney(this.configMask);
	}

	return Formatter;
}());

Erp.MaskPhoneNumber = (function() {

	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');
		this.maskBehavior = function(val) {
			return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000'
					: '(00) 0000-00009';
		};

		this.options = {
			onKeyPress : function(val, e, field, options) {
				field.mask(maskBehavior.apply({}, arguments), options);
			}
		};
	}

	MaskPhoneNumber.prototype.init = function() {
		this.inputPhoneNumber.mask(this.maskBehavior, this.options);
	}

	return MaskPhoneNumber;
}());

Erp.MaskCpfCnpj = (function() {
	function MaskCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpf-cnpj]');
		this.inputCpfCnpj = $('#cpf-cnpj');
	}

	MaskCpfCnpj.prototype.init = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaChanged.bind(this));
	}

	function onTipoPessoaChanged(event) {
		var tipoPessoa = $(event.currentTarget);
		this.labelCpfCnpj.text(tipoPessoa.data('doc-type'));
		// At first I tried to use a reserved word "mask" and generated an error
		this.inputCpfCnpj.mask(tipoPessoa.data('mask-pattern'));
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.removeAttr('disabled');
	}

	return MaskCpfCnpj;
}());

Erp.MaskCep = (function() {
	function MaskCep() {
		this.inputCep = $('#cep');
	}

	MaskCep.prototype.init = function() {
		this.inputCep.mask("00.000-000");
	}

	return MaskCep;
}());
// criei aqui para agilizar os testes - transferir para um arquivo independente
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
	}

	ComboCidade.prototype.init = function() {
		reset.call(this);
		this.comboEstado.on('alterado', onEstadoChange.bind(this));
	}

	function onEstadoChange(event, estado) {
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
	}

	function reset() {
		this.combo.html('<option value="">Selecione uma cidade</option>');
		this.combo.val('');
		this.combo.attr('disabled', 'disabled');
	}
	return ComboCidade;
}());

$(function() {
	new Erp.Formatter().enable();

	new Erp.MaskPhoneNumber().init();

	new Erp.MaskCpfCnpj().init();

	new Erp.MaskCep().init();

	var comboEstado = new Erp.ComboEstado();
	comboEstado.init();

	new Erp.ComboCidade(comboEstado).init();
});