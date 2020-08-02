package com.example.demo.board.service;

import com.example.demo.board.domain.FreeBoard;
import com.example.demo.board.domain.FreeBoardFile;
import com.example.demo.board.reposiroty.FreeBoardFileRepository;
import com.example.demo.board.reposiroty.FreeBoardRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FreeBoardService {

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Autowired
    private FreeBoardFileRepository freeBoardFileRepository;

    @Transactional
    public FreeBoard save(FreeBoard freeBoard) {
        this.freeBoardRepository.save(freeBoard);
        return freeBoard;
    }

    @Transactional
    public List<FreeBoard> findByPage(int startNum) {
        Pageable pageable = PageRequest.of(startNum, 10);
        Page<FreeBoard> page = this.freeBoardRepository.findAll(pageable);
        return page.getContent();
    }

    @Transactional
    public int getTotalCount() {
        return (int) this.freeBoardRepository.count();
    }

    @Transactional
    public List<FreeBoard> findByTitleContainingOrContentContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10);
        return this.freeBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable).getContent();
    }

    @Transactional
    public int getCountByTitleContainingOrContentContaining(String keyword) {
        return this.freeBoardRepository.countByTitleContainingOrContentContaining(keyword, keyword);
    }

    @Transactional
    public FreeBoard getFreeBoardDetail(long freeBoardId) {
        FreeBoard freeBoard = this.freeBoardRepository.findById(freeBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("FreeBoard", "id", "it can not find free board data"));

        freeBoard.setNumberOfHit((short) (freeBoard.getNumberOfHit() + 1));
        return this.freeBoardRepository.save(freeBoard);
    }
    
    @Transactional
	public FreeBoardFile save(FreeBoardFile freeBoardFile) {
		this.freeBoardFileRepository.save(freeBoardFile);
		return freeBoardFile;
	}
    
    @Transactional
    public void deleteById(long contentId) {
    	this.freeBoardRepository.deleteById(contentId);
    }
}