package com.community.web.service;

import com.community.web.domain.Board;
import com.community.web.domain.Comment;
import com.community.web.domain.User;
import com.community.web.dto.request.CommentRequestDto;
import com.community.web.dto.response.CommentResponseDto;
import com.community.web.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Page<CommentResponseDto> findCommentList(Long board_id, Pageable pageable){
        Page<Comment> commentPage = commentRepository.findAllByBoardIdxOrderByIdxDesc(board_id, pageable);
        return commentPage.map(CommentResponseDto::new);
    }

    public CommentResponseDto save(CommentRequestDto commentRequestDto, Board board, User user){
        return new CommentResponseDto(commentRepository.save(Comment.builder()
                .content(commentRequestDto.getContent())
                .board(board)
                .user(user)
                .build()));
    }

    public CommentResponseDto findCommentByIdx(Long idx){
        return new CommentResponseDto(commentRepository.findById(idx).get());
    }
}
