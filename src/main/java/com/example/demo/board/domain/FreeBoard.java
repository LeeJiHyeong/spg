package com.example.demo.board.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.login.domain.DateAudit;
import com.sun.xml.bind.v2.TODO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "free_board")
public class FreeBoard extends DateAudit {
	
	// TODO : id FK 적용
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@NotBlank
	@Size(max = 40)
	@Column(name = "title")
	private String title;
	
	@Column(name = "writer_id")
	private long writerId;
	
	@NotBlank
	@Size(max = 20)
	@Column(name = "writer_name")
	private String writerName;
	
	@NotBlank
	@Column(name = "content")
	private String content;
	
	public FreeBoard() {
		
	}
	
	public FreeBoard(String title, long writerId, String writerName, String content) {
		this.title = title;
		this.writerId = writerId;
		this.writerName = writerName;
		this.content = content;
	}
}
