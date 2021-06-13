package com.cnu.spg.domain.board;

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
@Table(name = "notice_board_file")
public class NoticeBoardFile {

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
	@Column(name = "notice_board_id")
	private Long noticeBoardId;
	
	public NoticeBoardFile() {}
	
	public NoticeBoardFile(String storeFileName, String ordinaryFileName, Long noticeBoardId) {
		this.storeFileName = storeFileName;
		this.ordinaryFileName = ordinaryFileName;
		this.noticeBoardId = noticeBoardId;
	}

}