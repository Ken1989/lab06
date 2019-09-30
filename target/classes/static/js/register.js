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

function registerUser() {
	let data = {
		name : $('#name').val(),
		email : $('#email').val(),
		username : $('#username').val(),
		password : $('#password').val(),
		admin : $('#admin').prop('checked'),
	};

	var link = /* [[@{/register}]] */'/register';

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : `${link}?kaptcha=` + $('#kaptcha').val(),
		data : JSON.stringify(data),
		dataType : 'json',
		timeout : 100000,
	}).done(function(res) {
		$.toast({
			heading : 'Success',
			text : 'Registration is successful.',
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