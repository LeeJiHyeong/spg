package com.cnu.spg.board.reposiroty;

import com.cnu.spg.board.FreeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardCommentRepositroy extends JpaRepository<FreeBoardComment, Long>{
	public int countByContentId(Long contentId);
}
