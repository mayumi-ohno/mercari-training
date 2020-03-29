$(function(){
	
	var parentUrl = "http://localhost:8080/category-list-api"
		
	$("select[name='parentCategoryId']").on("change", function(){
		var hostUrl = parentUrl + "/change-parent-category"
		var paerntCategoryId = $("select[name='parentCategoryId'] option:selected").val();

		$.ajax({
			url: hostUrl,
			type: "GET",
			dataType: "json",
			data:{
				paerntCategoryId: paerntCategoryId ,
			},
			async: true
		}).done(function(data){
			console.log(data);
			$("#passwordMessage").text(data.passwordMessage);
		})
	});
});