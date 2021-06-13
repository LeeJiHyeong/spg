package com.cnu.spg.domain.board.reposiroty;

import com.cnu.spg.domain.board.FreeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardCommentRepositroy extends JpaRepository<FreeBoardComment, Long>{
	public int countByContentId(Long contentId);
}
