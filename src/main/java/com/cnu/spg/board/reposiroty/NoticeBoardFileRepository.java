package com.cnu.spg.board.reposiroty;

import java.util.List;
import java.util.Optional;

import com.cnu.spg.board.domain.NoticeBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardFileRepository extends JpaRepository<NoticeBoardFile, String> {
    List<NoticeBoardFile> findAllByNoticeBoardId(Long NoticeBoardId);

    Optional<NoticeBoardFile> findByStoreFileName(String storeFileName);

    Optional<NoticeBoardFile> findByOrdinaryFileName(String ordinaryFileName);

    Optional<NoticeBoardFile> findByNoticeBoardId(Long NoticeBoardId);
}