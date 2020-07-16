package com.example.demo.board.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.board.domain.FreeBoardFile;
import com.example.demo.board.reposiroty.FreeBoardFileRepository;

@Service
public class FreeBoardFileService {
	
	@Autowired
	FreeBoardFileRepository freeBoardFileRepository;
	
	@Transactional
	public FreeBoardFile save(FreeBoardFile freeBoardFile) {
		this.freeBoardFileRepository.save(freeBoardFile);
		return freeBoardFile;
	}
}
