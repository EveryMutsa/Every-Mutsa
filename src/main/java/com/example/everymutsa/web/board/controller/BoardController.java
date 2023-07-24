package com.example.everymutsa.web.board.controller;

import com.example.everymutsa.web.board.domain.dto.BoardDto;
import com.example.everymutsa.web.board.domain.enums.BoardType;
import com.example.everymutsa.web.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/{schoolId}")
    public BoardDto create(
            @PathVariable("schoolId") Long schoolId,
            @RequestBody BoardDto dto
    ) {
        return boardService.save(schoolId,dto);
    }

    @GetMapping("/{schoolId}/{boardType}")
    public Page<BoardDto> readAll(
            @RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(name = "size", defaultValue = "5") Integer pageSize,
            @PathVariable("schoolId") Long schoolId,
            @PathVariable("boardType") String boardType
    ) {
        return boardService.readAll(pageNo,pageSize,schoolId,boardType);
    }

    @GetMapping("/{id}")
    public BoardDto read(
            @PathVariable("id") Long boardId
    ) {
        return boardService.readOne(boardId);
    }

    @PutMapping("/{id}")
    public BoardDto update(
            @PathVariable("id") Long boardId,
            @RequestBody BoardDto dto
    ) {
        return boardService.update(boardId, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long boardId
    ) {
        boardService.remove(boardId);
    }
}
