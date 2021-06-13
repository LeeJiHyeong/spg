package com.cnu.spg.board.service;

import com.cnu.spg.domain.board.reposiroty.FreeBoardFileRepository;
import com.cnu.spg.domain.board.FreeBoard;
import com.cnu.spg.domain.board.FreeBoardComment;
import com.cnu.spg.domain.board.FreeBoardFile;
import com.cnu.spg.domain.board.reposiroty.FreeBoardCommentRepositroy;
import com.cnu.spg.domain.board.reposiroty.FreeBoardRepository;
import com.cnu.spg.exception.ResourceNotFoundException;
import com.cnu.spg.utils.FilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Service
public class FreeBoardService {

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Autowired
    private FreeBoardFileRepository freeBoardFileRepository;
    
    @Autowired
    private FreeBoardCommentRepositroy freeBoardCommentRepository;

    @Transactional
    public FreeBoard save(FreeBoard freeBoard) {
        this.freeBoardRepository.save(freeBoard);
        return freeBoard;
    }

    @Transactional
    public List<FreeBoard> findByPage(int startNum) {
    	Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        Page<FreeBoard> page = this.freeBoardRepository.findAll(pageable);
        return page.getContent();
    }

    @Transactional
    public int getTotalCount() {
        return (int) this.freeBoardRepository.count();
    }

    @Transactional
    public List<FreeBoard> findByTitleContainingOrContentContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.freeBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable).getContent();
    }

    @Transactional
    public int getCountByTitleContainingOrContentContaining(String keyword) {
        return this.freeBoardRepository.countByTitleContainingOrContentContaining(keyword, keyword);
    }
    
    @Transactional
    public List<FreeBoard> findByWriterNameContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.freeBoardRepository.findByWriterNameContaining(keyword, pageable).getContent();
    }

    @Transactional
    public int getCountByWriterNameContaining(String keyword) {
        return this.freeBoardRepository.countByWriterNameContaining(keyword);
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
    public void deleteFilesAndFreeBoardDataByContentId(long contentId) {
        // todo kkh : what will we handle this
        boolean result = this.deleteFilesInList(this.freeBoardFileRepository.findAllByFreeBoardId(contentId));
        this.freeBoardRepository.deleteById(contentId);
    }

    @Transactional
    public boolean modifyFreeBoardDetail(FreeBoard newFreeBoard) {
        boolean isDeleteWell = this.deleteFilesInList(this.freeBoardFileRepository.findAllByFreeBoardId(newFreeBoard.getId()));
        if (isDeleteWell) this.freeBoardRepository.save(newFreeBoard);
        return isDeleteWell;
    }

    private boolean deleteFile(String filename) {
        File file = new File(FilePath.FreeBoard.getFilePath() + filename);
        if (file.exists()) {
            return file.delete();
        }

        return false;
    }

    private boolean deleteFilesInList(List<FreeBoardFile> freeBoardFileList) {
        boolean isDeleteError = true;
        for (FreeBoardFile freeBoardFile : freeBoardFileList) {
            if (!this.deleteFile(freeBoardFile.getStoreFileName())) {
                isDeleteError = false;
            }
        }

        return isDeleteError;
    }
    
    // comment
    @Transactional
    public FreeBoardComment save(FreeBoardComment freeBoardComment) {
    	this.freeBoardCommentRepository.save(freeBoardComment);
    	return freeBoardComment;
    }
    
    @Transactional
    public int getCommentCountByContentId(Long contentId) {
    	return this.freeBoardCommentRepository.countByContentId(contentId);
    }
    
    @Transactional
    public void deleteComment(Long commentId) {
    	this.freeBoardCommentRepository.deleteById(commentId);
    }
    
}