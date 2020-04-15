$(function() {
	
	$("button[id$=edit]").on("click", function() {
		var userId = $(this).attr("id").replace("edit", "");
		hideDefultText(userId);
		$("input[id="+userId+"email]").val($("span[class="+userId+"email]").text());
		var autority =$("span[class="+userId+"authority]").text();
		if(autority=="admin"){
			$("select[id="+userId+"authority]").val("1");
		}else{
			$("select[id="+userId+"authority]").val("2");
		}
	});
	
	$("input[id$=email]").on("change", function(){
		var userId = $(this).attr("id").replace("email", "");
		$("button[id=" +userId+"submit]").prop("desabled", false);
	});
	
	$("select[id$=authority]").on("change", function(){
		var userId = $(this).attr("id").replace("authority", "");
		$("button[id=" +userId+"submit]").prop("desabled", false);
	});
	
	$("button[id$=cancel]").on("click", function() {
		var userId = $(this).attr("id").replace("cancel", "");
		hideEditForm(userId);
	});
	
	$("button[id$=submit]").on("click", function() {
		var userId = $(this).attr("id").replace("submit", "");
		$("input[id="+userId+"submitEmail]").val($("input[id="+userId+"email]").val());
		$("input[id="+userId+"submitAuthority]").val($("select[id="+userId+"authority] option:selected").val());
	});
});

// 初期テキスト・editボタンを隠し、編集フォームを表示する.
function hideDefultText(userId){
	$("button[id=" +userId+"edit]").hide();
	$("button[id=" +userId+"delete]").hide();
	$("span[class="+userId+"email]").hide();
	$("span[class="+userId+"authority]").hide();
	$("button[id=" +userId+"cancel]").show();
	$("input[id="+userId+"email]").show();
	$("select[id="+userId+"authority]").show();
	$("button[id=" +userId+"submit]").prop("desabled", true);
	$("button[id=" +userId+"submit]").show();
	$("button[id=" +userId+"delete]").prop("desabled", false);
}
// 編集フォームを隠し、初期テキスト・editボタンを表示する.
function hideEditForm(userId){
	$("button[id=" +userId+"edit]").show();
	$("button[id=" +userId+"delete]").show();
	$("span[class="+userId+"email]").show();
	$("span[class="+userId+"authority]").show();
	$("button[id=" +userId+"cancel]").hide();
	$("input[id="+userId+"email]").hide();
	$("input[id="+userId+"email]").val("");
	$("select[id="+userId+"authority]").hide();
	$("select[id="+userId+"authority]").val("");
	$("button[id=" +userId+"submit]").hide();
	$("span[id='emailError']").hide();
	$("span[id='authorityError']").hide();
	$("button[id=" +userId+"delete]").prop("desabled", true);
}