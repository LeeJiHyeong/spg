$('#delete-button').click(function(){ 
	if (confirm("게시글을 삭제하시겠습니까?")) {
		var contentId = $('#content-id').val();
		location.href = "/board/freeBoard/doDelete?contentId=" + contentId;
	}
	else {
		return;
	}
});

function checkDeleteComment(commentId) {
	if (confirm("댓글을 삭제하시겠습니까?")) {
		alert(commentId);
	}
	else {
		return;
	}
}

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
		alert("내용을 입력해주세요.");
		return false;
	}
	
	return true;
}

function checkComment() {
	var comment = $('#comment-input').val();
	
	console.log(comment);
	
	if (comment == "") {
		alert("내용을 입력해주세요.");
		return false;
	}
	
	writeComment(comment);
	
	return true;
}

function writeComment(comment) {
	
	var contentId = $('#content-id').val();
	var allData = { "content": comment, 
					"contentId" : contentId};
	
    $.ajax({
        type:'GET',
        url : "/board/freeBoard/doWriteComment",
        dataType : "json",
        data : allData,
        success : function(data){
        	console.log(data);
        	
        	var addTag = '<div class="eachComment">'+
        					'<div class="name">' +
        						'<span>' + data.userName + '</span>' +
        						'<a th:href="@{#}"><span class="remove-btn">&times;</span></a>' +
        					'</div>' +
        					'<div class="inputValue">' +
								'<span>' + data.content +
								'</span>' +
							'</div>'
        				'</div>';
        	
        	var commentLocation = $('#comments').children().last();
        	commentLocation.after(addTag);
        	$('#comment-input').val('');
        	$('#count').text(data.commentCount);
        	console.log($('#count').text());
        },
        error : function(request, status, error){
        	console.log(error);
            alert("댓글 등록에 실패하였습니다.");
       }
    });
}

function deleteComment(commentId) {
	
	var allData = { "commentId": commentId };
	
//	$.ajax({
//        type:'GET',
//        url : "/board/freeBoard/doDeleteComment,
//        dataType : "json",
//        data : allData,
//        success : function(data){
//        	console.log(data);
//        	console.log(this);
//        },
//        error : function(request, status, error){
//        	console.log(error);
//            alert("댓글 등록에 실패하였습니다.");
//       }
//    });
}
