package com.example.demo.board.domain;

import java.util.Calendar;
import java.util.List;

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
@Table(name = "free_board")
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @Column(name = "number_of_hit")
    private short numberOfHit;

    @NotBlank
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_date")
    private Calendar createDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "free_board_id")
    private List<FreeBoardFile> freeBoardFile;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    private List<FreeBoardComment> freeBoardComment;

    public FreeBoard() {
    }

    public FreeBoard(String title, long writerId, String writerName, String content) {
        this.title = title;
        this.writerId = writerId;
        this.writerName = writerName;
        this.content = content;
    }
}