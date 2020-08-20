package com.example.demo.board.domain;

import java.util.Calendar;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "free_board_comment")
public class FreeBoardComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "user_name")
	@Size(max = 20)
    private String userName;
	
	@NotBlank
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
	
	@Column(name = "create_date")
    private Calendar createDate;
	
	@Column(name = "content_id")
	private Long contentId;
}
