var token = $("meta[name='_csrf']").attr("content");
$.ajaxSetup({
	beforeSend : function(xhr) {
		xhr.setRequestHeader('X-CSRF-TOKEN', token);
	}
});

function changeCaptcha() {
	var link = /* [[@{/captcha_image}]] */'test';
	$(".captcha-image").attr("src", `${link}?ts=${ Date.now()}`);
}

function updateUser() {
	if ($('#password').val() !== $('#password2').val()) {
		$.toast({
			heading : 'Error',
			text : 'password mismatch.',
			showHideTransition : 'fade',
			icon : 'error',
			position : 'top-right'
		})
		return false;
	}

	let data = {
		userId : $('#userId').val(),
		name : $('#name').val(),
		email : $('#email').val(),
		username : $('#username').val(),
		password : $('#password').val(),
		admin : $('#admin').prop('checked'),
	};

	var link = /* [[@{/user/account}]] */'/user/account';

	$.ajax({
		type : "PUT",
		contentType : "application/json",
		url : `${link}?kaptcha=` + $('#kaptcha').val(),
		data : JSON.stringify(data),
		dataType : 'json',
		timeout : 100000,
	}).done(function(res) {
		$.toast({
			heading : 'Success',
			text : 'Account updated successfully.',
			showHideTransition : 'slide',
			icon : 'success',
			position : 'top-right'
		})
	}).fail(function(res) {
		$.toast({
			heading : 'Error',
			text : res.responseJSON.message,
			showHideTransition : 'fade',
			icon : 'error',
			position : 'top-right'
		})
	}).always(function() {
		// ...
	});
}