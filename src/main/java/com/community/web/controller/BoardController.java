package com.community.web.controller;

import com.community.web.annotation.SocialUser;
import com.community.web.domain.Board;
import com.community.web.domain.User;
import com.community.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping({"/board"})
    public String board(@RequestParam(value="idx",defaultValue="0") Long idx, Model model, @SocialUser User user){
        Board board = boardService.findBoardByIdx(idx);
        model.addAttribute("board", board);
        if(board.getUser().equals(user))    model.addAttribute("authorized",true);
        return "/board/detail";
    }

    @GetMapping("/")
    public String list(@PageableDefault Pageable pageable, Model model){
        model.addAttribute("boardList",boardService.findBoardList(pageable));
        return "/board/list";
    }

    @GetMapping("/board/write")
    public String write(@RequestParam(value="idx", required = false) Long idx, Model model, @SocialUser User user){
        if(idx!=null){
            Board board = boardService.findBoardByIdx(idx);
            if(!board.getUser().equals(user)) return "redirect:/";
            model.addAttribute("board", board);
        }
        return "/board/form";
    }
}
