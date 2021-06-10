package com.cnu.spg.board.domain;

import java.util.Calendar;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "edu_board_comment")
public class EduBoardComment {
	
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
