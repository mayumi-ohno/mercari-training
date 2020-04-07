$(function() {

	change_status_edit_form();
	if(!$('#deleteError').length){
		$("#showEditForm").prop('disabled', true);
		$("#deleteButton").prop('disabled', true);
	}
	if($('#editButton').is(':visible')){
		$("#existingParentCategory").prop('disabled', true);
		$("#existingChildCategory").prop('disabled', true);
		$("#existingGrandChildCategory").prop('disabled', true);
	}

	//	親カテゴリを選択すると、編集・削除ボタンを有効化する
	$("#existingParentCategory").on("change", function() {
		if($("#existingParentCategory").val()!="" && $("#existingParentCategory")!=null){
			$("#showEditForm").prop('disabled', false);
			$("#deleteButton").prop('disabled', false);
		}else{
			$("#showEditForm").prop('disabled', true);
			$("#deleteButton").prop('disabled', true);
		}
	});
	
	//	editボタンを押下したときの処理
	$("#showEditForm").on("click", function() {
		
		$('#deleteError').text("");
		
		// カテゴリのセレクトボックスを無効化（cancelしない限り選択値固定）する
		$("#existingParentCategory").prop('disabled', true);
		$("#existingChildCategory").prop('disabled', true);
		$("#existingGrandChildCategory").prop('disabled', true);
		$("#showEditForm").prop('disabled', true);
		$("#deleteButton").prop('disabled', true);
		
		// 親-子-孫各カテゴリの編集フォーム表示/非表示設定
		change_status_edit_form();
		
		// 選択中のカテゴリ名を編集フォームの初期値として設定
		if($("#existingParentCategory option:selected").val()!="" && $("#existingParentCategory option:selected")!=null){
		$("#editParentCategory").val($("#existingParentCategory option:selected").text());
		}
		if($("#existingChildCategory option:selected").val()!="" && $("#existingChildCategory option:selected")!=null){
		$("#editChildCategory").val($("#existingChildCategory option:selected").text());
		}
		if($("#existingGrandChildCategory option:selected").val()!="" && $("#existingGrandChildCategory option:selected")!=null){
		$("#editGrandChildCategory").val($("#existingGrandChildCategory option:selected").text());
		}
	});
	
	//	cancelボタンを押下したときの処理
	$("#cancel").on("click", function() {
		
		// 親-子-孫各カテゴリのセレクトボックス有効/無効設定
		$("#existingParentCategory").prop('disabled', false);
		
		if ($("#existingParentCategory").val() == "") {
			$("#existingChildCategory").prop('disabled', true);
		} else {
			$("#existingChildCategory").prop('disabled', false);
		}

		if ($("#existingChildCategory").val() == "" || $("#existingChildCategory").val() == "") {
			$("#existingGrandChildCategory").prop('disabled', true);
		} else {
			$("#existingGrandChildCategory").prop('disabled', false);
		}
	
		// 編集フォームを非表示にする
		$("#editParentCategory").hide();
		$("#editChildCategory").hide();
		$("#editGrandChildCategory").hide();
		$("#editMessage").hide();
		$("#edieError").hide();
		$("#editButton").hide();
		$("#cancel").hide();
		$("#showEditForm").prop('disabled', false);
		$("#deleteButton").prop('disabled', false);
	});
	
	//	submitボタンを押下したときの処理
	$("#editButton").on("click", function() {
		$("#existingParentCategory").prop('disabled', false);
		$("#existingChildCategory").prop('disabled', false);
		$("#existingGrandChildCategory").prop('disabled', false);
	});
	// ---------------------------------------------------------------------------

});

// 親-子-孫各カテゴリの編集フォーム表示/非表示設定
function change_status_edit_form() {
	var parentCategory = $("#existingParentCategory option:selected").val();
	var childCategory = $("#existingChildCategory option:selected").val();
	var grandChildCategory = $("#existingGrandChildCategory option:selected").val();
	if($('#deleteError').text()=="" && parentCategory !="" && parentCategory!=null){
		$("#editParentCategory").show();
		$("#editParentCategory").prop('readonly',false);
		$("#editMessage").show();
		$("#editButton").show();
		$("#cancel").show();
	}else{
		$("#editParentCategory").hide();
		$("#editMessage").hide();
		$("#editButton").hide();
		$("#cancel").hide();
	}

	if($('#deleteError').text()=="" && childCategory!="" && childCategory!=null){
		$("#editChildCategory").show();
		$("#editChildCategory").prop('readonly',false);
		$("#editParentCategory").prop('readonly',true);
	}else{
		$("#editChildCategory").hide();
	}

	if($('#deleteError').text()=="" && grandChildCategory!="" && grandChildCategory!=null){
		$("#editGrandChildCategory").show();
		$("#editChildCategory").prop('readonly',true);
	}else{
		$("#editGrandChildCategory").hide();
	}
}