package com.cnu.spg.domain.board.reposiroty;

import java.util.Optional;

import com.cnu.spg.domain.board.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

	public Optional<FreeBoard> findById(Long id);

	public Page<FreeBoard> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyWord, Pageable pageable);
	
	public Page<FreeBoard> findByWriterNameContaining(String keyWord, Pageable pageable);
	
	public int countByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
	
	public int countByWriterNameContaining(String keyWord);
}