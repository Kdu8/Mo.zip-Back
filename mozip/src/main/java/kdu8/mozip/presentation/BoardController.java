package kdu8.mozip.presentation;

import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.BoardRequest;
import kdu8.mozip.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    @PostMapping("")
    public ResponseEntity<Board> createBoard(@RequestBody BoardRequest boardRequest, HttpServletRequest request) throws Exception {
        User user;
        try {
            HttpSession session = request.getSession(false);
            user = (User) session.getAttribute("user");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Board board = boardService.createBoard(user, boardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @GetMapping("/{id}")
    public Board getBoard(@PathVariable int id) throws Exception {
           return boardService.getBoard(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@RequestBody BoardRequest boardRequest, HttpServletRequest request, @PathVariable int id) throws Exception {
        User user;
        try {
            HttpSession session = request.getSession(false);
            user = (User) session.getAttribute("user");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Board board = boardService.updateBoard(user, boardRequest, id);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(HttpServletRequest request, @PathVariable int id) throws Exception {
        User user;
        try {
            HttpSession session = request.getSession(false);
            user = (User) session.getAttribute("user");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        boardService.deleteBoard(user, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }

    @GetMapping("/{id}/toggle-apply")
    public ResponseEntity<String> toggleApply(HttpServletRequest request, @PathVariable int id) throws Exception{
        User user;
        try {
            HttpSession session = request.getSession(false);
            user = (User) session.getAttribute("user");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Board board = boardService.getBoard(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }
}
