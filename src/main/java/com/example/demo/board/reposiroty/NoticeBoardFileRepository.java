package com.example.demo.board.reposiroty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.board.domain.NoticeBoardFile;

public interface NoticeBoardFileRepository extends JpaRepository<NoticeBoardFile, String> {
    List<NoticeBoardFile> findAllByNoticeBoardId(Long NoticeBoardId);

    Optional<NoticeBoardFile> findByStoreFileName(String storeFileName);

    Optional<NoticeBoardFile> findByOrdinaryFileName(String ordinaryFileName);

    Optional<NoticeBoardFile> findByNoticeBoardId(Long NoticeBoardId);
}