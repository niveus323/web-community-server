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
    private Long view;

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
        this.view = 0L;
        this.setCreatedDateNow();
        this.setUpdatedDateNow();
    }

    public Board update(String title, String content, BoardType boardType){
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.setUpdatedDateNow();
        return this;
    }

    public Board updateView(){
        this.view++;
        return this;
    }
}
