$(function() {
	if(!$("#parentError").length){
		$("#formsForAddParent").hide();
	}
	if(!$("#childError").length && !$("#parentIsBlank").length){
		$("#formsForAddChild").hide();
	}
	if(!$("#grandChildError").length && !$("#childIsBlank").length){
		$("#formsForAddGrandChild").hide();
	}
	if($("#selectParent2 option:selected").val() !=""){
		$("input[name='childCategoryName']").prop('disabled', false);
	}else{
		$("input[name='childCategoryName']").prop('disabled', true);
	}
	if($("#selectChild option:selected").val() !=""){
		$("input[name='grandChildCategoryName']").prop('disabled', false);
	}else{
		$("input[name='grandChildCategoryName']").prop('disabled', true);
	}
	
	var $parentCatogories =$("#selectParent2"); // 親カテゴリの要素を変数化
	var $childCtegories = $("#selectChild"); // 子カテゴリの要素を変数化
	var originalChildCategories = $childCtegories.html(); // 後のイベントで、不要なoption要素を削除するため、オリジナルをとっておく
	
	if($parentCatogories.val()==""){
		$childCtegories.attr('disabled', 'disabled');
	}

	//	フォームの表示/非表示--------------------------
	$("#addParentCategory").on("click", function() {
		$("#formsForAddParent").show();
		$("#formsForAddChild").hide();
		$("#formsForAddGrandChild").hide();
	});

	$("#addChildCategory").on("click", function() {
		$("#formsForAddChild").show();
		$("#formsForAddParent").hide();
		$("#formsForAddGrandChild").hide();
	});

	$("#addGrandChildCategory").on("click", function() {
		$("#formsForAddGrandChild").show();
		$("#formsForAddParent").hide();
		$("#formsForAddChild").hide();
		$("input[name='grandChildCategoryName']").prop('disabled', true);
	});
	
	$("#selectParent1").on("change", function(){
		if($("#selectParent1 option:selected").val() !=""){
			$("input[name='childCategoryName']").prop('disabled', false);
		}else{
			$("input[name='childCategoryName']").prop('disabled', true);
		}
	});
	
	$childCtegories.on("change", function(){
		if($("#selectChild option:selected").val() !=""){
			$("input[name='grandChildCategoryName']").prop('disabled', false);
		}else{
			$("input[name='grandChildCategoryName']").prop('disabled', true);
		}
	});
	
	// -------子カテゴリリスト編集メソッド-----------------------------------------------
	$parentCatogories.change(function() {
		// 選択された親カテゴリのvalueを取得し変数化
		var parentSelected = $parentCatogories.val();

		// 削除された要素をもとに戻すため.html(original)を入れておく
		$childCtegories.html(originalChildCategories).find('option').each(function() {
			var parentOfChild = $(this).data('val'); // data-valの値を取得

			// valueと異なるdata-valを持つ要素を削除
			if (parentSelected != parentOfChild) {
				$(this).not(':first-child').remove();
			}

		});

		if ($parentCatogories.val() == "") {
			$childCtegories.prop('disabled', true);
		} else {
			$childCtegories.prop('disabled', false);
		}
		
		if ($parentCatogories.val() == "") {
			$childCtegories.val("");
		}
		
	});
});	
