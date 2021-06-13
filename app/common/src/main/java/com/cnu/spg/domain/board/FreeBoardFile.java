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
@Table(name = "free_board_file")
public class FreeBoardFile {

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
	@Column(name = "free_board_id")
	private Long freeBoardId;
	
	public FreeBoardFile() {}
	
	public FreeBoardFile(String storeFileName, String ordinaryFileName, Long freeBoardId) {
		this.storeFileName = storeFileName;
		this.ordinaryFileName = ordinaryFileName;
		this.freeBoardId = freeBoardId;
	}

}