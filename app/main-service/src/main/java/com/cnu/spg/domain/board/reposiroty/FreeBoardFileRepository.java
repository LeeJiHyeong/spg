package com.cnu.spg.domain.board.reposiroty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cnu.spg.domain.board.FreeBoardFile;

public interface FreeBoardFileRepository extends JpaRepository<FreeBoardFile, String> {
    List<FreeBoardFile> findAllByFreeBoardId(Long freeBoardId);

    Optional<FreeBoardFile> findByStoreFileName(String storeFileName);

    Optional<FreeBoardFile> findByOrdinaryFileName(String ordinaryFileName);

    Optional<FreeBoardFile> findByFreeBoardId(Long freeBoardId);
}