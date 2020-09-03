package com.example.demo.board.service;

import com.example.demo.board.domain.EduBoard;
import com.example.demo.board.domain.EduBoardComment;
import com.example.demo.board.domain.EduBoardFile;
import com.example.demo.board.reposiroty.EduBoardCommentRepository;
import com.example.demo.board.reposiroty.EduBoardFileRepository;
import com.example.demo.board.reposiroty.EduBoardRepository;
import com.example.demo.board.reposiroty.EduBoardCommentRepository;
import com.example.demo.board.reposiroty.EduBoardFileRepository;
import com.example.demo.board.reposiroty.EduBoardRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.utils.FilePath;
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
public class EduBoardService {

    @Autowired
    private EduBoardRepository eduBoardRepository;

    @Autowired
    private EduBoardFileRepository eduBoardFileRepository;
    
    @Autowired
    private EduBoardCommentRepository eduBoardCommentRepository;

    @Transactional
    public EduBoard save(EduBoard eduBoard) {
        this.eduBoardRepository.save(eduBoard);
        return eduBoard;
    }

    @Transactional
    public List<EduBoard> findByPage(int startNum) {
    	Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        Page<EduBoard> page = this.eduBoardRepository.findAll(pageable);
        return page.getContent();
    }

    @Transactional
    public int getTotalCount() {
        return (int) this.eduBoardRepository.count();
    }

    @Transactional
    public List<EduBoard> findByTitleContainingOrContentContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.eduBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable).getContent();
    }

    @Transactional
    public int getCountByTitleContainingOrContentContaining(String keyword) {
        return this.eduBoardRepository.countByTitleContainingOrContentContaining(keyword, keyword);
    }
    
    @Transactional
    public List<EduBoard> findByWriterNameContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.eduBoardRepository.findByWriterNameContaining(keyword, pageable).getContent();
    }

    @Transactional
    public int getCountByWriterNameContaining(String keyword) {
        return this.eduBoardRepository.countByWriterNameContaining(keyword);
    }

    @Transactional
    public EduBoard getEduBoardDetail(long EduBoardId) {
        EduBoard EduBoard = this.eduBoardRepository.findById(EduBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("EduBoard", "id", "it can not find Edu board data"));

        EduBoard.setNumberOfHit((short) (EduBoard.getNumberOfHit() + 1));
        return this.eduBoardRepository.save(EduBoard);
    }

    @Transactional
    public EduBoardFile save(EduBoardFile eduBoardFile) {
        this.eduBoardFileRepository.save(eduBoardFile);
        return eduBoardFile;
    }

    @Transactional
    public void deleteFilesAndEduBoardDataByContentId(long contentId) {
        // todo kkh : what will we handle this
        boolean result = this.deleteFilesInList(this.eduBoardFileRepository.findAllByEduBoardId(contentId));
        this.eduBoardRepository.deleteById(contentId);
    }

    @Transactional
    public boolean modifyEduBoardDetail(EduBoard newEduBoard) {
        boolean isDeleteWell = this.deleteFilesInList(this.eduBoardFileRepository.findAllByEduBoardId(newEduBoard.getId()));
        if (isDeleteWell) this.eduBoardRepository.save(newEduBoard);
        return isDeleteWell;
    }

    private boolean deleteFile(String filename) {
        File file = new File(FilePath.EduBoard.getFilePath() + filename);
        if (file.exists()) {
            return file.delete();
        }

        return false;
    }

    private boolean deleteFilesInList(List<EduBoardFile> eduBoardFileList) {
        boolean isDeleteError = true;
        for (EduBoardFile eduBoardFile : eduBoardFileList) {
            if (!this.deleteFile(eduBoardFile.getStoreFileName())) {
                isDeleteError = false;
            }
        }

        return isDeleteError;
    }
    
    // comment
    @Transactional
    public EduBoardComment save(EduBoardComment eduBoardComment) {
    	this.eduBoardCommentRepository.save(eduBoardComment);
    	return eduBoardComment;
    }
    
    @Transactional
    public int getCommentCountByContentId(Long contentId) {
    	return this.eduBoardCommentRepository.countByContentId(contentId);
    }
    
    @Transactional
    public void deleteComment(Long commentId) {
    	this.eduBoardCommentRepository.deleteById(commentId);
    }
    
}