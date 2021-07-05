package com.community.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Comment extends BaseEntity{
    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOARD_ID", updatable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Board board;

    @ManyToOne
    @JoinColumn(name="USER_ID", updatable = false)
    private User user;

    @Builder
    public Comment(String content, Board board, User user) {
        this.board = board;
        this.content = content;
        this.user = user;
        this.setCreatedDateNow();
        this.setUpdatedDateNow();
    }
}
