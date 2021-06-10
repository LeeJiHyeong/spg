package com.cnu.spg.board.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnu.spg.board.domain.EduBoardComment;

public interface EduBoardCommentRepository extends JpaRepository<EduBoardComment, Long>{
	public int countByContentId(Long contentId);
}
