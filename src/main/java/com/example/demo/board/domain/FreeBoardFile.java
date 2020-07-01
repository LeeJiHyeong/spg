package com.example.demo.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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

	@NotBlank
	@Column(name = "free_board_id")
	private Long freeBoardId;

}