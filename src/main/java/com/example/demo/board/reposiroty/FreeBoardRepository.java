package com.example.demo.board.reposiroty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.board.domain.FreeBoard;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

	public Optional<FreeBoard> findById(Long id);

	public Page<FreeBoard> findByTitleContainingOrContentContaining(String aString, String bString, Pageable pageable);

	public List<FreeBoard> findByWriterName(String writerName);
	
	public int countByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
}