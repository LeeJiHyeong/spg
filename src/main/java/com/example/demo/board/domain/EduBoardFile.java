package com.example.demo.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "edu_board_file")
public class EduBoardFile {

	@Id
	@NotBlank
	@Size(max = 60)
	@Column(name = "store_filename")
	private String storeFileName;

	@NotBlank
	@Size(max = 60)
	@Column(name = "ordinary_filename")
	private String ordinaryFileName;

	@NotNull
	@Column(name = "edu_board_id")
	private Long eduBoardId;
	
	public EduBoardFile() {}
	
	public EduBoardFile(String storeFileName, String ordinaryFileName, Long eduBoardId) {
		this.storeFileName = storeFileName;
		this.ordinaryFileName = ordinaryFileName;
		this.eduBoardId = eduBoardId;
	}

}