package com.example.demo.board.reposiroty;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.board.domain.FreeBoardFile;

public interface FreeBoardFileRepository extends JpaRepository<FreeBoardFile, String> {

	public Optional<FreeBoardFile> findByStoreFileName(String storeFileName);

	public Optional<FreeBoardFile> findByOrdinaryFileName(String ordinaryFileName);

	public Optional<FreeBoardFile> findByFreeBoardId(Long freeBoardId);
}