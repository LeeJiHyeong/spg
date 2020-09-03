package com.example.demo.board.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.domain.NoticeBoardComment;

public interface NoticeBoardCommentRepository extends JpaRepository<NoticeBoardComment, Long>{
	public int countByContentId(Long contentId);
}
