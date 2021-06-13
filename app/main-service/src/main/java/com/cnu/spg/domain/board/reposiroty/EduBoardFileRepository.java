package com.cnu.spg.domain.board.reposiroty;

import java.util.List;
import java.util.Optional;

import com.cnu.spg.domain.board.EduBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EduBoardFileRepository extends JpaRepository<EduBoardFile, String> {
    List<EduBoardFile> findAllByEduBoardId(Long eduBoardId);

    Optional<EduBoardFile> findByStoreFileName(String storeFileName);

    Optional<EduBoardFile> findByOrdinaryFileName(String ordinaryFileName);

    Optional<EduBoardFile> findByEduBoardId(Long eduBoardId);
}