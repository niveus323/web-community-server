package com.community.web.service;

import com.community.web.domain.Board;
import com.community.web.domain.User;
import com.community.web.domain.enums.BoardType;
import com.community.web.domain.projection.BoardWithUser;
import com.community.web.dto.request.BoardRequestDto;
import com.community.web.dto.response.BoardResponseDto;
import com.community.web.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public Page<BoardResponseDto> findBoardList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1, pageable.getPageSize());
        return boardRepository.findAllByOrderByBoardTypeDescIdxDesc(pageable, BoardWithUser.class).map(BoardResponseDto::new);
    }

    public Page<BoardResponseDto> findBoardListWithKeyword(Pageable pageable, String keyword){
        String regex = Arrays.stream(keyword.split(" ")).map(x -> String.format("(?=.*%s)",x)).collect(Collectors.joining(""));
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1, pageable.getPageSize());
        return boardRepository.findAllByTitleOrContentRegexOrderByIdxDesc(regex,pageable, BoardWithUser.class).map(BoardResponseDto::new);
    }

    @Transactional
    public BoardResponseDto findBoardByIdx(Long idx){
        Board board = boardRepository.findById(idx).get();
        return new BoardResponseDto(board.updateView());
    }

    public Board findEntityByIdx(Long idx) {
        return boardRepository.findById(idx).get();
    }

    public BoardResponseDto save(BoardRequestDto boardRequestDto, User user){
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .boardType(BoardType.valueOf(boardRequestDto.getBoardType()))
                .user(user)
                .build();
        return new BoardResponseDto(boardRepository.save(board));
    }

    @Transactional
    public boolean update(Long idx,BoardRequestDto boardRequestDto){
        return boardRepository.updateBoardById(idx, boardRequestDto.getTitle(), boardRequestDto.getContent(), BoardType.valueOf(boardRequestDto.getBoardType())) > 0;
    }

    @Transactional
    public boolean addVote(Long idx, Long userIdx){
        return boardRepository.voteBoardById(userIdx, idx) > 0;
    }
}
