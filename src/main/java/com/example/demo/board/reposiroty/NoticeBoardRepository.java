package com.example.demo.board.reposiroty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.board.domain.NoticeBoard;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

	public Optional<NoticeBoard> findById(Long id);

	public Page<NoticeBoard> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyWord, Pageable pageable);
	
	public Page<NoticeBoard> findByWriterNameContaining(String keyWord, Pageable pageable);
	
	public int countByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
	
	public int countByWriterNameContaining(String keyWord);
}