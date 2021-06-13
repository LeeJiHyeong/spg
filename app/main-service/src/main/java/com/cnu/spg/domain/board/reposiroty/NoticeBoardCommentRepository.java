package com.cnu.spg.domain.board.reposiroty;

import com.cnu.spg.domain.board.NoticeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardCommentRepository extends JpaRepository<NoticeBoardComment, Long>{
	public int countByContentId(Long contentId);
}
