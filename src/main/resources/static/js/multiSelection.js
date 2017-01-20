Erp = Erp || {};

Erp.MultiSelection = (function(){
	function MultiSelection(){
		this.changeBtn = $('.js-status-btn');
		this.selectionCheckbox = $('.js-selection-checkbox');
	}
	
	MultiSelection.prototype.init = function(){
		this.changeBtn.on('click', onStatusChangeBtn.bind(this))
	}
	
	function onStatusChangeBtn(event){
		var clickButton = $(event.currentTarget);
		var status = clickButton.data('status');
		var checkboxSelected = this.selectionCheckbox.filter(':checked');
		var codigos = $.map(checkboxSelected, function(c){
			return $(c).data('codigo');
		});
		
		if(codigos.length > 0){
			$.ajax({
				url: '/erp/usuarios/status',
				method: 'PUT',
				data: {
					codigos: codigos,
					status: status
				},
				success: function(){
					window.location.reload();
				}
			});
		}
	}
	
	return MultiSelection;
}());

$(function(){
	new Erp.MultiSelection().init();
});