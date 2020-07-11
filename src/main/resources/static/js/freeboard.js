function checkContent() {
	var title = $('#content-title').val();
	var content = $('#comment-message').val();
	
	console.log(title);
	console.log(content);
	
	if (title == "") {
		alert("제목을 입력해주세요.");
		return false;
	}
	
	if (content == "") {
		alert("내용을 입력해주세요.")
		return false;
	}
	
	return true;
}