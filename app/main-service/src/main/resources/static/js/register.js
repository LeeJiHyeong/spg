function validate(){
	var regexp = /^[0-9]*$/;
	var id = document.getElementById("register-username");

	if(!check(regexp, id, "아이디는 숫자로 이루어져야 합니다.")){
	return false;
	}
	
	if(document.getElementById("register-password").value != document.getElementById("register-password2").value){
		alert("비밀번호가 다릅니다. 확인해 주세요.");
		document.getElementById("register-password2").value = "";
		document.getElementById("register-password2").focus();
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
