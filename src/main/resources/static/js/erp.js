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
		var tipoPessoaSelected = this.radioTipoPessoa.filter(':checked')[0];
		if (tipoPessoaSelected) {
			applyMask.call(this, $(tipoPessoaSelected));
		}
	}

	function onTipoPessoaChanged(event) {
		var tipoPessoa = $(event.currentTarget);
		applyMask.call(this, tipoPessoa);
		this.inputCpfCnpj.val('');
	}

	function applyMask(tipoPessoaSelected) {
		this.labelCpfCnpj.text(tipoPessoaSelected.data('doc-type'));
		// At first I tried to use a reserved word "mask" and generated an error
		this.inputCpfCnpj.mask(tipoPessoaSelected.data('mask-pattern'));
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

Erp.MaskDate = (function() {
	function MaskDate() {
		this.inputDate = $('.js-date');
	}
	/* Attributes of datepicker see: https://bootstrap-datepicker.readthedocs.io/en/latest/options.html */
	MaskDate.prototype.init = function() {
		// this.inputDate.mask('00/00/0000', {placeholder: "__/__/____"});
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation : 'bottom',
			language : 'pt-BR',
			todayHighlight : true,
			autoclose : true
		});
	}

	return MaskDate;
}());

Erp.Security = (function(){
	function Security(){
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header').val();
	}
	
	Security.prototype.init = function(){
		$(document).ajaxSend(function(event, jqxhr, settings){
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
}());
// setting locale
numeral.locale('pt-br');

Erp.formatarMoeda = function(valor){
	// It is necessary that the value be of type number
	return numeral(Number(valor)).format('0,0.00');
};

Erp.undoFormatting = function(valor){
	return numeral(valor)._value;
}

Erp.MaskTime = (function(){
	function MaskTime(){
		this.inputTime = $('.js-time');
	}
	
	MaskTime.prototype.init = function(){
		this.inputTime.mask('00:00');
	}
	
	return MaskTime;
}());

$(function() {
	new Erp.Formatter().enable();

	new Erp.MaskPhoneNumber().init();

	new Erp.MaskCpfCnpj().init();

	new Erp.MaskCep().init();

	new Erp.MaskDate().init();
	
	new Erp.Security().init();
	
	new Erp.MaskTime().init();
	
});