package com.community.web.controller;

import com.community.web.annotation.SocialUser;
import com.community.web.dto.response.BoardResponseDto;
import com.community.web.dto.UserDto;
import com.community.web.dto.response.CommentResponseDto;
import com.community.web.service.BoardService;
import com.community.web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

    final BoardService boardService;
    final CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @GetMapping({"/board"})
    public String board(@RequestParam(value="idx",defaultValue="0") Long idx, Model model, @PageableDefault Pageable pageable){
        BoardResponseDto boardResponseDto = boardService.findBoardByIdx(idx);
        model.addAttribute("board", boardResponseDto);
        model.addAttribute("commentList",commentService.findCommentList(boardService.findEntityByIdx(idx),pageable));
        return "/board/detail";
    }

    @GetMapping("/")
    public String list(@PageableDefault Pageable pageable, Model model){
        model.addAttribute("boardList",boardService.findBoardList(pageable));
        return "/board/list";
    }

    @GetMapping("/board/write")
    public String write(@RequestParam(value="idx", required = false) Long idx, Model model, @SocialUser UserDto userDto){
        if(idx!=null){
            BoardResponseDto board = boardService.findBoardByIdx(idx);
            if(!board.getUser().equals(userDto)) return "redirect:/";
            model.addAttribute("board", board);
        }
        return "/board/form";
    }

    @GetMapping("/board/comment_edit/{comment_idx}")
    public String comment_form(@PathVariable("comment_idx") Long idx, Model model, @SocialUser UserDto userDto){
        CommentResponseDto commentResponseDto = commentService.findCommentByIdx(idx);
        if(!commentResponseDto.getUser().equals(userDto)) return "redirect:/";
        model.addAttribute("comment",commentResponseDto);
        return "/board/comment_form";
    }
}
