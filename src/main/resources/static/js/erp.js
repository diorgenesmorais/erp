var Erp = Erp || {};

Erp.Formatter = (function(){
	
	function Formatter(){
		this.configMask = {
			decimal : ',',
			thousands : '.',
			allowZero : true	
		};
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	Formatter.prototype.enable = function(){
		this.decimal.maskMoney(this.configMask);
		
		this.configMask.precision = 0;
		
		this.plain.maskMoney(this.configMask);
	}
	
	return Formatter;
}());

$(function() {
	var formatter = new Erp.Formatter();
	formatter.enable();
});