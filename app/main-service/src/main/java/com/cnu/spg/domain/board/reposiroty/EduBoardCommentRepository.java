package com.cnu.spg.domain.board.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnu.spg.domain.board.EduBoardComment;

public interface EduBoardCommentRepository extends JpaRepository<EduBoardComment, Long>{
	public int countByContentId(Long contentId);
}
