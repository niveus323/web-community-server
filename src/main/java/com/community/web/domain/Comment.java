package com.community.web.domain;

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
    private String context;

    @ManyToOne
    @JoinColumn(name="BOARD_ID")
    private Board board;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @Builder
    public Comment(String context, Board board, User user) {
        this.board = board;
        this.context = context;
        this.user = user;
    }
}
