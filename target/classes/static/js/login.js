function changeCaptcha() {
	$(".captcha-image").attr("src", `${link}?ts=${ Date.now()}`);
}