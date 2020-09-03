package com.example.demo.board.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.domain.EduBoardComment;

public interface EduBoardCommentRepository extends JpaRepository<EduBoardComment, Long>{
	public int countByContentId(Long contentId);
}
