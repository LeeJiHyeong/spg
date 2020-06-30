package com.example.demo.board.reposiroty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.domain.FreeBoard;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>{
	
}
