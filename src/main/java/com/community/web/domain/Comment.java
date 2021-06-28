package com.community.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Comment extends BaseEntity{
    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name="BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Builder
    public Comment(String content, Board board, User user) {
        this.board = board;
        this.content = content;
        this.user = user;
    }
}
