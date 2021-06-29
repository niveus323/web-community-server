package com.community.web.domain;

import com.community.web.domain.enums.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table
public class Board extends BaseEntity{
    @Column
    private String title;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne
    private User user;

    @Builder
    public Board(String title, String content, BoardType boardType, User user) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.user = user;
    }

    public Board update(String title, String content, BoardType boardType){
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        return this;
    }
}
