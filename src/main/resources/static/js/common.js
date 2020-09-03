function freeBoardSearch() {
	var searchType = $('#search-type').val();
	var searchKey = $('#search_key').val();
	
	// 셀렉 옵션 고정
	// 검색 키워드 고정
	location.href = "/board/freeBoard?searchKey=" + searchKey + "&searchType=" + searchType;
}

function noticeBoardSearch() {
	var searchType = $('#search-type').val();
	var searchKey = $('#search_key').val();
	
	// 셀렉 옵션 고정
	// 검색 키워드 고정
	location.href = "/board/noticeBoard?searchKey=" + searchKey + "&searchType=" + searchType;
}