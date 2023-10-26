package com.example.team_project.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.team_project.board.board_category.BoardCategory;
import com.example.team_project.board.board_pic.BoardPic;
import com.example.team_project.user.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board_tb")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String boardTitle;

    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardCategory boardCategory;

    private Timestamp boardCreatedAt;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<BoardPic> boardpics = new ArrayList<>();

    @Builder
    public Board(Integer id, String boardTitle, String boardContent, User user, BoardCategory boardCategory,
            Timestamp boardCreatedAt) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.user = user;
        this.boardCategory = boardCategory;
        this.boardCreatedAt = boardCreatedAt;
    }

}
