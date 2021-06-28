package com.community.web.service;

import com.community.web.domain.Board;
import com.community.web.domain.Comment;
import com.community.web.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Page<Comment> findCommentList(Board board, Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0 : pageable.getPageNumber()-1, pageable.getPageSize());
        return commentRepository.findAllByBoard(board,pageable);
    }
}
