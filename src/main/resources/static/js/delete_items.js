$(function() {

	$("#deleteButton").prop('disabled', true);

	$('input[type="checkbox"]').on("change", function() {
		if ($('input[name="delete"]:checked').length > 0) {
			$("#deleteButton").prop('disabled', false);
		}
		if ($('input[name="delete"]:checked').length == 0) {
			$("#deleteButton").prop('disabled', true);
		}
	});

	$('#selectAll').on("click", function() {
		$('input[name="delete"]').prop("checked", $(this).prop("checked"));
	});

	$("#deleteButton").on("click", function() {

		var itemIdList = $('input[name="delete"]:checked').map(function() {
			return $(this).val();
		}).get();

		$("#deleteId").val(itemIdList);

	});
});
