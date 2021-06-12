package com.cnu.spg.board.reposiroty;

import java.util.Optional;

import com.cnu.spg.board.EduBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EduBoardRepository extends JpaRepository<EduBoard, Long> {

	public Optional<EduBoard> findById(Long id);

	public Page<EduBoard> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyWord, Pageable pageable);
	
	public Page<EduBoard> findByWriterNameContaining(String keyWord, Pageable pageable);
	
	public int countByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
	
	public int countByWriterNameContaining(String keyWord);
}