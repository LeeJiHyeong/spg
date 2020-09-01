$(document).ready(function() {
	$(".hamburger").click(function() {
		$(this).toggleClass("is-active");
	});
});

function openHamburger() {
	var class_by_id = $('#hamburger-button').attr('class');
	if (class_by_id == 'hamburger') {
		document.getElementById("beside-hamburger").style.width = "100%";
		document.getElementById("hamburger-button-container").style.width = "80%";
		document.getElementById("hamburger-container").style.width = "70%";
	} else {
		document.getElementById("hamburger-container").style.width = "0px";
		document.getElementById("hamburger-button-container").style.width = "50px";
		document.getElementById("beside-hamburger").style.width = "0px";
	}
}
