package com.community.web.controller;

import com.community.web.dto.UserDto;
import com.community.web.dto.request.BoardRequestDto;
import com.community.web.dto.request.CommentRequestDto;
import com.community.web.service.BoardService;
import com.community.web.service.CommentService;
import com.community.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RepositoryRestController
public class BoardRestController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public BoardRestController(BoardService boardService, CommentService commentService, UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.boardService = boardService;
        this.commentService = commentService;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping(value = "/boards", produces = "application/json; charset=utf8")
    public @ResponseBody
    ResponseEntity<?> saveBoard(@RequestBody BoardRequestDto boardDto, HttpSession session){
        UserDto userDto = (UserDto) session.getAttribute("user");
        return ResponseEntity.ok(boardService.save(boardDto, userService.toEntity(userDto)));
    }

    @PutMapping(value="/boards/{board_id}", produces = "application/json; charset=utf8")
    public @ResponseBody
    ResponseEntity<?> updateBoard(@PathVariable("board_id") Long idx, @RequestBody BoardRequestDto boardRequestDto){
        return ResponseEntity.ok(boardService.update(idx, boardRequestDto));
    }

    @PostMapping(value="/comments/{board_id}", produces = "application/json; charset=utf8")
    public @ResponseBody
    ResponseEntity<?> saveComment(@PathVariable("board_id") Long idx, @RequestBody CommentRequestDto commentRequestDto, HttpSession session){
        UserDto userDto = (UserDto) session.getAttribute("user");
        simpMessagingTemplate.convertAndSend("/board/"+idx,"");
        return ResponseEntity.ok(commentService.save(commentRequestDto, boardService.findEntityByIdx(idx),userService.toEntity(userDto)));
    }

    @GetMapping(value="/comments/{board_id}")
    public @ResponseBody ResponseEntity<?> getComment(@PathVariable("board_id") Long idx, @PageableDefault Pageable pageable){
        return new ResponseEntity<>( commentService.findCommentList(idx, pageable), HttpStatus.OK);
    }
}
