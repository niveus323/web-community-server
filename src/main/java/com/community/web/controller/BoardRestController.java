package com.community.web.controller;

import com.community.web.domain.Board;
import com.community.web.domain.Comment;
import com.community.web.domain.User;
import com.community.web.domain.enums.BoardType;
import com.community.web.dto.UserDto;
import com.community.web.dto.request.BoardRequestDto;
import com.community.web.dto.request.CommentRequestDto;
import com.community.web.dto.response.BoardResponseDto;
import com.community.web.dto.response.CommentResponseDto;
import com.community.web.service.BoardService;
import com.community.web.service.CommentService;
import com.community.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class BoardRestController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public BoardRestController(BoardService boardService, CommentService commentService, UserService userService) {
        this.boardService = boardService;
        this.commentService = commentService;
        this.userService = userService;
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
        return ResponseEntity.ok(commentService.save(commentRequestDto, boardService.findEntityByIdx(idx),userService.toEntity(userDto)));
    }

    @GetMapping(value="/comments/{board_id}")
    public @ResponseBody ResponseEntity<?> getComment(@PathVariable("board_id") Long idx, @RequestParam Long lastCommentId){
       return new ResponseEntity<>( commentService.findCommentList(idx, lastCommentId,PageRequest.of(0,10)), HttpStatus.OK);
    }
}
