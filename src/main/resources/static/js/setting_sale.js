$(function() {

	if(!$("#dateError").length){
		$("#saleButton").prop('disabled', true);
		$("#saleSubmit").prop('disabled', true);
		$("#saleForm").hide();
	}

	$('input[name="sale"]').on("change", function() {
		$("#saleForm").hide();
		
		if ($('input[name="sale"]:checked').length > 0) {
			$("#saleButton").prop('disabled', false);
		}
		if ($('input[name="sale"]:checked').length == 0) {
			$("#saleButton").prop('disabled', true);
		}
	});

	$('#selectAll').on("click", function() {
		$('input[name="sale"]').prop("checked", $(this).prop("checked"));
		$("#saleForm").hide();
		
		if ($('input[name="sale"]:checked').length > 0) {
			$("#saleButton").prop('disabled', false);
		}
		if ($('input[name="sale"]:checked').length == 0) {
			$("#saleButton").prop('disabled', true);
		}
	});

	$("#saleButton").on("click", function() {

		var itemIdList = $('input[name="sale"]:checked').map(function() {
			return $(this).val();
		}).get();

		$("#saleId").val(itemIdList);

		$("#saleForm").show();
	});
	
	$('#saleForm').on("change", function() {
		$("#saleSubmit").prop('disabled', true);
		var startDate = $("input[name='start']").val();
		var endDate = $("input[name='end']").val();
		var discountRate = $("input[name='discountRate']").val();
		var startInvalid = startDate!=null && startDate!="";
		var endInvalid = endDate!=null && endDate!="";
		var discountRateInvalid = 0<discountRate && discountRate<=100;
		if( startInvalid && endInvalid && discountRateInvalid){
			$("#saleSubmit").prop('disabled', false);
		}
	});
});
