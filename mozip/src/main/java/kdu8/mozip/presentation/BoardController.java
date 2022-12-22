package kdu8.mozip.presentation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.BoardListResponse;
import kdu8.mozip.presentation.dto.BoardRequest;
import kdu8.mozip.presentation.dto.BoardResponse;
import kdu8.mozip.service.ApplicantService;
import kdu8.mozip.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final ApplicantService applicantService;

    @GetMapping("")
    @ApiOperation(value = "BoardList 가져오기", notes = "parameter에 PageNumber를 같이 전해줘야 함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<BoardListResponse>> getBoards(Pageable pageable) throws Exception {
        List<BoardListResponse> boardPage = boardService.getBoardListWithPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(boardPage);
    }
    @PostMapping("")
    @GetMapping("")
    @ApiOperation(value = "Board 만들기", notes = "세션에 로그인이 되어 있어야함")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "로그인 되지 않음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
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
    @ApiOperation(value = "Board 가져오기", notes = "Id에는 BoardId가 들어감")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BoardResponse> getBoard(@PathVariable int id) throws Exception {
           return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoard(id));
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Board 업데이트 하기", notes = "로그인이 되어 있어야함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "로그인 되지 않음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
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
    @ApiOperation(value = "Board 삭제하기", notes = "로그인이 되어 있어야함")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
            @ApiResponse(code = 401, message = "로그인 되지 않음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
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
    @ApiOperation(value = "Board에 신청하기", notes = "로그인이 되어 있어야함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "로그인 되지 않음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> toggleApply(HttpServletRequest request, @PathVariable int id) throws Exception{
        User user;
        try {
            HttpSession session = request.getSession(false);
            user = (User) session.getAttribute("user");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        applicantService.toggleApply(boardService.getBoard(id).getBoard(), user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
