// 参考URL: https://try-m.co.jp/blog/web-create/936/
$(function() {
	
	var $parentCatogories =$("select[name='parentCategoryId']"); // 親カテゴリの要素を変数化
	var $childCtegories = $("select[name='childCategoryId']"); // 子カテゴリの要素を変数化
	var $grandChildCtegories = $("select[name='grandChildCategoryId']"); // 孫カテゴリの要素を変数化
	var originalChildCategories = $childCtegories.html(); // 後のイベントで、不要なoption要素を削除するため、オリジナルをとっておく
	var originalGrandChildCategories = $grandChildCtegories.html(); // 同上
	
	changeStatusOfChildCategories(); 
	changeStatusOfGrandChildCategories();

	// 親カテゴリ未選択の場合：子・孫カテゴリも未選択にする
	// 親カテゴリ選択済の場合：子カテゴリリストを編集
	if ($parentCatogories.val() == "") {
		$grandChildCtegories.val("");
		$childCtegories.val("");
	}else{
		modifyChildCategories();
	}
	
	// 子カテゴリ未選択の場合：孫カテゴリも未選択にする
	// 子カテゴリ選択済の場合：孫カテゴリリストを編集
	if ($childCtegories.val() == "") {
		$grandChildCtegories.val("");
	}else{
		modifyGrandChildCategories();
	}
	
	$parentCatogories.change(function() {
		modifyChildCategories();
	});
	
	$childCtegories.change(function() {
		modifyGrandChildCategories();
	});
		
	// -------子カテゴリリスト編集メソッド-----------------------------------------------
	function modifyChildCategories() {		
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

		changeStatusOfChildCategories();
		changeStatusOfGrandChildCategories();
		if ($parentCatogories.val() == "") {
			$grandChildCtegories.val("");
			$childCtegories.val("");
		}
	}
	
	// -------孫カテゴリリスト編集メソッド-----------------------------------------------
	function modifyGrandChildCategories() {
		// 選択された子カテゴリのvalueを取得し変数化
		var childSelected = $childCtegories.val();
		
		// 削除された要素をもとに戻すため.html(original)を入れておく
		$grandChildCtegories.html(originalGrandChildCategories).find('option').each(function() {
			var parentOfGrandChild = $(this).data('val'); // data-valの値を取得
			
			// valueと異なるdata-valを持つ要素を削除
			if (childSelected != parentOfGrandChild) {
				$(this).not(':first-child').remove();
			}
			
		});
		
		changeStatusOfGrandChildCategories();
		if ($childCtegories.val() == "") {
			$grandChildCtegories.val("");
		}
	}
	
		// -------子・孫カテゴリセレクトボックスの有効/無効切り替えメソッド-----------------------------------------------
		function changeStatusOfChildCategories() {
			if ($parentCatogories.val() == "") {
				$childCtegories.attr('disabled', 'disabled');
			} else {
				$childCtegories.removeAttr('disabled');
			}
		}
		function changeStatusOfGrandChildCategories() {
			if ($childCtegories.val() == "" || $parentCatogories.val() == "") {
				$grandChildCtegories.attr('disabled', 'disabled');
			} else {
				$grandChildCtegories.removeAttr('disabled');
			}
		}
});
