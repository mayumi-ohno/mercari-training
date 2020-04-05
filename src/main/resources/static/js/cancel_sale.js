$(function() {

	$("#cancelButton").prop('disabled', true);

	$('input[name="cancelDiscount"]').on("change", function() {
		
		if ($('input[name="cancelDiscount"]:checked').length > 0) {
			$("#cancelButton").prop('disabled', false);
		}
		if ($('input[name="cancelDiscount"]:checked').length == 0) {
			$("#cancelButton").prop('disabled', true);
		}
	});

	$('#cancelAll').on("click", function() {
		$('input[name="cancelDiscount"]').prop("checked", $(this).prop("checked"));
		
		if ($('input[name="cancelDiscount"]:checked').length > 0) {
			$("#cancelButton").prop('disabled', false);
		}
		if ($('input[name="cancelDiscount"]:checked').length == 0) {
			$("#cancelButton").prop('disabled', true);
		}
	});

	$("#cancelButton").on("click", function() {

		var itemIdList = $('input[name="cancelDiscount"]:checked').map(function() {
			return $(this).val();
		}).get();

		$("#cancelId").val(itemIdList);

	});
});
