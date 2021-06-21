package com.community.web.domain.event;

import com.community.web.domain.Board;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler
public class BoardEventHandler {
    @HandleBeforeCreate
    public void beforeCreateBoard(Board board){
        board.setCreatedDateNow();
    }

    @HandleBeforeSave
    public void beforeSaveBoard(Board board){
        board.setUpdatedDateNow();
    }
}

