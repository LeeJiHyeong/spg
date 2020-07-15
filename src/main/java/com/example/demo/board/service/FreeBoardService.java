package com.example.demo.board.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.board.domain.FreeBoard;
import com.example.demo.board.reposiroty.FreeBoardRepository;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public class FreeBoardService {

	@Autowired
	FreeBoardRepository freeBoardRepository;

	@Transactional
	public FreeBoard save(FreeBoard freeBoard) {
		this.freeBoardRepository.save(freeBoard);
		return freeBoard;
	}

	@Transactional
	public List<FreeBoard> findAll() {
		return this.freeBoardRepository.findAll();
	}
	
	@Transactional
	public FreeBoard findById(Long id) {
		return this.freeBoardRepository.findById(id).orElseGet(() -> null);
	}
	
	@Transactional
	public List<FreeBoard> findByPage(int startNum) {
		Pageable pageable = PageRequest.of(startNum, 10);
		Page<FreeBoard> page = this.freeBoardRepository.findAll(pageable);
		return page.getContent();
	}
	
	@Transactional
	public int getTotalCount() {
		return (int)this.freeBoardRepository.count();
	}
	
	@Transactional
	public List<FreeBoard> findByTitleContainingOrContentContaining(int startNum, String aString) {
		Pageable pageable = PageRequest.of(startNum, 10);
		return this.freeBoardRepository.findByTitleContainingOrContentContaining(aString, aString, pageable).getContent();
	}
	
	@Transactional
	public int getCountByTitleContainingOrContentContaining(String aString) {
		return this.freeBoardRepository.countByTitleContainingOrContentContaining(aString, aString);
	}
	
}