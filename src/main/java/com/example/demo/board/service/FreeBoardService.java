package com.example.demo.board.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.board.domain.FreeBoard;
import com.example.demo.board.reposiroty.FreeBoardRepository;

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

	public FreeBoardService() {
	}
}