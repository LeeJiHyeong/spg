function validate(){
	var regexp = /^[0-9]*$/;
	var id = document.getElementById("login-username");

	if(!check(regexp, id, "아이디는 숫자로 이루어져야 합니다.")){
	return false;
	}

	function check(regexp, what, message){
		if(regexp.test(what.value)){
			return true;
		}
		alert(message);
		what.value = "";
		what.focus();
	}
}
