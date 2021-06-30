package com.community.web.service;

import com.community.web.domain.Board;
import com.community.web.domain.Comment;
import com.community.web.domain.User;
import com.community.web.dto.request.CommentRequestDto;
import com.community.web.dto.response.CommentResponseDto;
import com.community.web.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Page<CommentResponseDto> findCommentList(Long board_id, Long idx,Pageable pageable){
        return commentRepository.findAllByBoardIdxAndIdxLessThanOrderByIdxDesc(board_id, idx,pageable).map(CommentResponseDto::new);
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
