$(function() {
	$("#submitSaleItemsSearching").prop('disabled', true);
	if($("#searchName").val()=="" && $("#searchBrand").val()=="" &&
			$("#searchParent option:selected").val()==""&&
			$("#searchChild option:selected").val()=="" &&
			$("#searchGrandChild option:selected").val()=="" ){
		$('#selectAllMatchingSearch').prop('disabled', true);
	}
	
	if(!$("input[name='cancelDiscount']").length){
		$("#cancelAll").prop('disabled', true);
	}

		$("#saleItemsSearchingForm").hide();
		$("#saleButton").prop('disabled', true);
		$("#saleSubmit").prop('disabled', true);
		$("#saleForm").hide();

	$('input[name="sale"]').on("change", function() {
		$("#saleForm").hide();
		$('#selectAll').prop("checked", false);
		
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
		var startDate = $("#startInSaleForm").val();
		var endDate = $("#endtInSaleForm").val();
		var discountRate = $("#rateInSaleForm").val();
		var startInvalid = startDate!=null && startDate!="";
		var endInvalid = endDate!=null && endDate!="";
		var discountRateInvalid = 0<discountRate && discountRate<=100;
		if( startInvalid && endInvalid && discountRateInvalid){
			$("#saleSubmit").prop('disabled', false);
		}
	});
	
	$('#saleItemsSearchingForm').on("change", function() {
		$("#submitSaleItemsSearching").prop('disabled', true);
		var startDate = $("#startInSaleItemsSearchingForm").val();
		var endDate = $("#endInSaleItemsSearchingForm").val();
		var discountRate = $("#rateInSaleItemsSearchingForm").val();
		var startInvalid = startDate!=null && startDate!="";
		var endInvalid = endDate!=null && endDate!="";
		var discountRateInvalid = 0<discountRate && discountRate<=100;
		if( startInvalid && endInvalid && discountRateInvalid){
			$("#submitSaleItemsSearching").prop('disabled', false);
		}
	});
	
	$('#selectAllMatchingSearch').on("change", function(){
		
		if($("#selectAllMatchingSearch").prop("checked") == true){
			$("#searchingName").val($("#searchName").val());
			$("#searchingParent").val($("#searchParent option:selected").val());
			$("#searchingChild").val($("#searchChild option:selected").val());
			$("#searchingGrandChild").val($("#searchGrandChild option:selected").val());
			$("#searchingBrand").val($("#searchBrand").val());
			$("#saleItemsSearchingForm").show();
			$("input[name='sale']").prop('disabled', true);
			$('#selectAll').prop('disabled', true);
			$("#saleButton").prop('disabled', true);
		}
		if($("#selectAllMatchingSearch").prop("checked") == false){
			$("#saleItemsSearchingForm").hide();
			$('#selectAll').prop('disabled', false);
			$("input[name='sale']").prop('disabled', false);
			$("#saleForm").hide();
		}
		
		});
	});
