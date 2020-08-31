function freeBoardSearch() {
	var searchKey = $('#search_key').val();
	location.href = "/board/freeBoard?searchKey=" + searchKey;
}