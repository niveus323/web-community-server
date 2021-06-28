package com.community.web.controller;

import com.community.web.domain.Board;
import com.community.web.domain.User;
import com.community.web.domain.enums.BoardType;
import com.community.web.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class BoardRestController {
    private final BoardRepository boardRepository;

    public BoardRestController(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @GetMapping("/boards")
    public @ResponseBody
    CollectionModel<Board> simpleBoard(@PageableDefault Pageable pageable){
        Page<Board> boardList = boardRepository.findAll(pageable);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageable.getPageSize(), boardList.getNumber(), boardList.getTotalElements());
        PagedModel<Board> resources = PagedModel.of(boardList.getContent(),pageMetadata);
        resources.add(linkTo(methodOn(BoardRestController.class).simpleBoard(pageable)).withSelfRel());
        return resources;
    }

    @PostMapping(value = "/boards", produces = "application/json; charset=utf8")
    public @ResponseBody
    ResponseEntity<?> saveBoard(@RequestBody Map<String, Object> param, HttpSession session){
        User user = (User) session.getAttribute("user");
        Board board = Board.builder().user(user)
                .boardType(BoardType.valueOf(param.get("boardType").toString()))
                .content(param.get("content").toString())
                .title(param.get("title").toString())
                .subTitle(param.get("subTitle").toString())
                .build();
        return ResponseEntity.ok(boardRepository.save(board));
    }

    @PutMapping(value="/boards", produces = "application/json; charset=utf8")
    public @ResponseBody
    ResponseEntity<?> updateBoard(@RequestBody Map<String, Object> param){
        Board board = boardRepository.findById((Long)param.get("id")).get();
        return ResponseEntity.ok(
                board.update(
                    param.get("title").toString(),
                    param.get("subTitle").toString(),
                    param.get("content").toString(),
                    BoardType.valueOf(param.get("boardType").toString())));
    }

}
