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
	
}
