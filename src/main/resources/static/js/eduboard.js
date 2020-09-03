$('#delete-button').click(function(){ 
	if (confirm("게시글을 삭제하시겠습니까?")) {
		var contentId = $('#content-id').val();
		location.href = "/board/eduBoard/doDelete?contentId=" + contentId;
	}
	else {
		return;
	}
});

function checkDeleteComment(commentId) {
	if (confirm("댓글을 삭제하시겠습니까?")) {
		deleteComment(commentId);
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
        url : "/board/eduBoard/doWriteComment",
        dataType : "json",
        data : allData,
        success : function(data){
        	console.log(data);
        	
        	var addTag = '<div class="eachComment" id="'+ data.commentId +'">'+
        					'<div class="name">' +
        						'<span>' + data.userName + '</span>' +
        						'<a onclick="checkDeleteComment(' + data.commentId + ')"><span class="remove-btn">&times;</span></a>' +
        					'</div>' +
        					'<div class="inputValue">' +
								'<p>' + data.content +
								'</p>' +
								'<p class="time-stamp">' + data.createDate +
								'</p>' +
							'</div>' +
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
	var contentId = $('#content-id').val();
	var allData = { "commentId" : commentId,
					"contentId" : contentId};
	
	$.ajax({
        type:'GET',
        url : "/board/eduBoard/doDeleteComment",
        dataType : "json",
        data : allData,
        success : function(data){
        	$('#' + commentId).remove();
        	$('#count').text(data.commentCount);
        },
        error : function(request, status, error){
            alert("댓글 삭제에 실패하였습니다.");
       }
    });
}
