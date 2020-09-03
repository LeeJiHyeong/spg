package com.example.demo.board.reposiroty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.domain.EduBoardFile;

public interface EduBoardFileRepository extends JpaRepository<EduBoardFile, String> {
    List<EduBoardFile> findAllByEduBoardId(Long eduBoardId);

    Optional<EduBoardFile> findByStoreFileName(String storeFileName);

    Optional<EduBoardFile> findByOrdinaryFileName(String ordinaryFileName);

    Optional<EduBoardFile> findByEduBoardId(Long eduBoardId);
}