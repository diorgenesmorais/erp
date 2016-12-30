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

Erp.MaskCpfCnpj = (function(){
	function MaskCpfCnpj(){
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpf-cnpj]');
		this.inputCpfCnpj = $('#cpf-cnpj');
	}
	
	MaskCpfCnpj.prototype.init = function(){
		this.radioTipoPessoa.on('change', onTipoPessoaChanged.bind(this));
	}
	
	function onTipoPessoaChanged(event){
		var tipoPessoa = $(event.currentTarget);
		this.labelCpfCnpj.text(tipoPessoa.data('doc-type'));
		// At first I tried to use a reserved word "mask" and generated an error
		this.inputCpfCnpj.mask(tipoPessoa.data('mask-pattern'));
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.removeAttr('disabled');
	}
	
	return MaskCpfCnpj;
}());

$(function() {
	var formatter = new Erp.Formatter();
	formatter.enable();

	var phoneNumber = new Erp.MaskPhoneNumber();
	phoneNumber.init();
	
	var maskCpfCnpj = new Erp.MaskCpfCnpj();
	maskCpfCnpj.init();
});