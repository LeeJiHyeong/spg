package com.example.demo.board.reposiroty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.board.domain.FreeBoard;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

	public Optional<FreeBoard> findById(Long id);

	public List<FreeBoard> findByTitleLike(String title);

	public List<FreeBoard> findByWriterName(String writerName);
}