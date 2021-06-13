package com.cnu.spg.board.service;

import com.cnu.spg.domain.board.NoticeBoard;
import com.cnu.spg.domain.board.NoticeBoardComment;
import com.cnu.spg.domain.board.NoticeBoardFile;
import com.cnu.spg.domain.board.reposiroty.NoticeBoardRepository;
import com.cnu.spg.domain.board.reposiroty.NoticeBoardCommentRepository;
import com.cnu.spg.domain.board.reposiroty.NoticeBoardFileRepository;
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
public class NoticeBoardService {

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Autowired
    private NoticeBoardFileRepository noticeBoardFileRepository;
    
    @Autowired
    private NoticeBoardCommentRepository noticeBoardCommentRepository;

    @Transactional
    public NoticeBoard save(NoticeBoard noticeBoard) {
        this.noticeBoardRepository.save(noticeBoard);
        return noticeBoard;
    }

    @Transactional
    public List<NoticeBoard> findByPage(int startNum) {
    	Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        Page<NoticeBoard> page = this.noticeBoardRepository.findAll(pageable);
        return page.getContent();
    }

    @Transactional
    public int getTotalCount() {
        return (int) this.noticeBoardRepository.count();
    }

    @Transactional
    public List<NoticeBoard> findByTitleContainingOrContentContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.noticeBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable).getContent();
    }

    @Transactional
    public int getCountByTitleContainingOrContentContaining(String keyword) {
        return this.noticeBoardRepository.countByTitleContainingOrContentContaining(keyword, keyword);
    }
    
    @Transactional
    public List<NoticeBoard> findByWriterNameContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.noticeBoardRepository.findByWriterNameContaining(keyword, pageable).getContent();
    }

    @Transactional
    public int getCountByWriterNameContaining(String keyword) {
        return this.noticeBoardRepository.countByWriterNameContaining(keyword);
    }

    @Transactional
    public NoticeBoard getNoticeBoardDetail(long noticeBoardId) {
        NoticeBoard noticeBoard = this.noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("NoticeBoard", "id", "it can not find Notice board data"));

        noticeBoard.setNumberOfHit((short) (noticeBoard.getNumberOfHit() + 1));
        return this.noticeBoardRepository.save(noticeBoard);
    }

    @Transactional
    public NoticeBoardFile save(NoticeBoardFile noticeBoardFile) {
        this.noticeBoardFileRepository.save(noticeBoardFile);
        return noticeBoardFile;
    }

    @Transactional
    public void deleteFilesAndNoticeBoardDataByContentId(long contentId) {
        // todo kkh : what will we handle this
        boolean result = this.deleteFilesInList(this.noticeBoardFileRepository.findAllByNoticeBoardId(contentId));
        this.noticeBoardRepository.deleteById(contentId);
    }

    @Transactional
    public boolean modifyNoticeBoardDetail(NoticeBoard newNoticeBoard) {
        boolean isDeleteWell = this.deleteFilesInList(this.noticeBoardFileRepository.findAllByNoticeBoardId(newNoticeBoard.getId()));
        if (isDeleteWell) this.noticeBoardRepository.save(newNoticeBoard);
        return isDeleteWell;
    }

    private boolean deleteFile(String filename) {
        File file = new File(FilePath.NoticeBoard.getFilePath() + filename);
        if (file.exists()) {
            return file.delete();
        }

        return false;
    }

    private boolean deleteFilesInList(List<NoticeBoardFile> noticeBoardFileList) {
        boolean isDeleteError = true;
        for (NoticeBoardFile noticeBoardFile : noticeBoardFileList) {
            if (!this.deleteFile(noticeBoardFile.getStoreFileName())) {
                isDeleteError = false;
            }
        }

        return isDeleteError;
    }
    
    // comment
    @Transactional
    public NoticeBoardComment save(NoticeBoardComment noticeBoardComment) {
    	this.noticeBoardCommentRepository.save(noticeBoardComment);
    	return noticeBoardComment;
    }
    
    @Transactional
    public int getCommentCountByContentId(Long contentId) {
    	return this.noticeBoardCommentRepository.countByContentId(contentId);
    }
    
    @Transactional
    public void deleteComment(Long commentId) {
    	this.noticeBoardCommentRepository.deleteById(commentId);
    }
    
}