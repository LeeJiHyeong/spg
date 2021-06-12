package com.cnu.spg.board.service;

import com.cnu.spg.utils.FilePath;
import com.cnu.spg.board.EduBoard;
import com.cnu.spg.board.EduBoardComment;
import com.cnu.spg.board.EduBoardFile;
import com.cnu.spg.board.reposiroty.EduBoardCommentRepository;
import com.cnu.spg.board.reposiroty.EduBoardFileRepository;
import com.cnu.spg.board.reposiroty.EduBoardRepository;
import com.cnu.spg.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EduBoardService {

    private final EduBoardRepository eduBoardRepository;
    private final EduBoardFileRepository eduBoardFileRepository;
    private final EduBoardCommentRepository eduBoardCommentRepository;

    @Transactional
    public EduBoard save(EduBoard eduBoard) {
        this.eduBoardRepository.save(eduBoard);
        return eduBoard;
    }

    public List<EduBoard> findByPage(int startNum) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        Page<EduBoard> page = this.eduBoardRepository.findAll(pageable);
        return page.getContent();
    }

    public int getTotalCount() {
        return (int) this.eduBoardRepository.count();
    }

    public List<EduBoard> findByTitleContainingOrContentContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.eduBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable).getContent();
    }

    @Transactional
    public int getCountByTitleContainingOrContentContaining(String keyword) {
        return this.eduBoardRepository.countByTitleContainingOrContentContaining(keyword, keyword);
    }

    public List<EduBoard> findByWriterNameContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.eduBoardRepository.findByWriterNameContaining(keyword, pageable).getContent();
    }

    public int getCountByWriterNameContaining(String keyword) {
        return this.eduBoardRepository.countByWriterNameContaining(keyword);
    }

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

    public int getCommentCountByContentId(Long contentId) {
        return this.eduBoardCommentRepository.countByContentId(contentId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        this.eduBoardCommentRepository.deleteById(commentId);
    }

}