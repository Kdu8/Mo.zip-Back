package kdu8.mozip.presentation;

import io.swagger.annotations.ApiParam;
import kdu8.mozip.entity.Board;
import kdu8.mozip.presentation.dto.RegisterRequest;
import kdu8.mozip.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public ResponseEntity<Page<Board>> getBoards(Pageable pageable) throws Exception {
        Page<Board> boardPage = boardService.getBoardListWithPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(boardPage);
    }

    @GetMapping("/{id}")
    public Board getBoard(@PathVariable int id) throws Exception {
           return boardService.getBoard(id);
    }
}
