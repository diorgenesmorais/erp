Erp = Erp || {};

Erp.MultiSelection = (function() {
	function MultiSelection() {
		this.changeBtn = $('.js-status-btn');
		this.selectionCheckbox = $('.js-selection-checkbox');
		this.selectionAllCheckbox = $('.js-selection-all');
		this.url = this.changeBtn.data('url');
	}

	MultiSelection.prototype.init = function() {
		this.changeBtn.on('click', onStatusChangeBtn.bind(this))
		this.selectionAllCheckbox.on('change', onSelectedAllCheckbox.bind(this));
		this.selectionCheckbox.on('change', onSelectedCheckbox.bind(this));
	}

	function onStatusChangeBtn(event) {
		var clickButton = $(event.currentTarget);
		var status = clickButton.data('status');
		var checkboxSelected = this.selectionCheckbox.filter(':checked');
		var codigos = $.map(checkboxSelected, function(c) {
			return $(c).data('codigo');
		});

		if (codigos.length > 0) {
			$.ajax({
				url : this.url,
				method : 'PUT',
				data : {
					codigos : codigos,
					status : status
				},
				success : function() {
					window.location.reload();
				}
			});
		}
	}

	function onSelectedAllCheckbox() {
		var statusSelected = this.selectionAllCheckbox.prop('checked');
		this.selectionCheckbox.prop('checked', statusSelected);
		activateButtons.call(this, statusSelected);
	}

	function onSelectedCheckbox() {
		var checkboxChecked = this.selectionCheckbox.filter(':checked');
		this.selectionAllCheckbox.prop('checked', checkboxChecked.length >= this.selectionCheckbox.length);
		activateButtons.call(this, checkboxChecked.length);
	}

	function activateButtons(active) {
		active ? this.changeBtn.removeClass('disabled') : this.changeBtn.addClass('disabled');
	}

	return MultiSelection;
}());

$(function() {
	new Erp.MultiSelection().init();
});