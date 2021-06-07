package com.cnu.spg.board.reposiroty;

import com.cnu.spg.board.domain.NoticeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardCommentRepository extends JpaRepository<NoticeBoardComment, Long>{
	public int countByContentId(Long contentId);
}
