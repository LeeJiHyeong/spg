package com.example.demo.board.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.domain.FreeBoardComment;

public interface FreeBoardCommentRepositroy extends JpaRepository<FreeBoardComment, Long>{

}
