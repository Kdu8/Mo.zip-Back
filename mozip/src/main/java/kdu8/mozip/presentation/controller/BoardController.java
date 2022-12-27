package kdu8.mozip.presentation.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.User;
import kdu8.mozip.exception.BoardDoesntExistException;
import kdu8.mozip.exception.CanNotApplyException;
import kdu8.mozip.exception.NotWriterException;
import kdu8.mozip.exception.UserDoesntExistException;
import kdu8.mozip.presentation.dto.board.BoardListResponse;
import kdu8.mozip.presentation.dto.board.BoardRequest;
import kdu8.mozip.presentation.dto.board.BoardResponse;
import kdu8.mozip.service.ApplicantService;
import kdu8.mozip.service.BoardService;
import kdu8.mozip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(origins = "127.0.0.1")
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final ApplicantService applicantService;

    private final UserService userService;

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

        try {
            User user = userService.authUser(request);
            Board board = boardService.createBoard(user, boardRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(board);
        } catch (UserDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Board 가져오기", notes = "Id에는 BoardId가 들어감")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "게시글이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 오류")

    })
    public ResponseEntity<BoardResponse> getBoard(@PathVariable int id) throws Exception {
           try {
               return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoard(id));
           }catch (BoardDoesntExistException e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
           }
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Board 업데이트 하기", notes = "로그인이 되어 있어야함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "작성자가 아님"),
            @ApiResponse(code = 401, message = "로그인 되지 않음"),
            @ApiResponse(code = 404, message = "게시글이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Board> updateBoard(@RequestBody BoardRequest boardRequest, HttpServletRequest request, @PathVariable int id) throws Exception {

        try {
            User user = userService.authUser(request);
            Board board = boardService.updateBoard(user, boardRequest, id);
            return ResponseEntity.status(HttpStatus.OK).body(board);
        } catch (UserDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }catch (NotWriterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (BoardDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Board 삭제하기", notes = "로그인이 되어 있어야함")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
            @ApiResponse(code = 400, message = "작성자가 아님"),
            @ApiResponse(code = 401, message = "로그인 되지 않음"),
            @ApiResponse(code = 404, message = "게시글이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> deleteBoard(HttpServletRequest request, @PathVariable int id) throws Exception {

        try {
            User user = userService.authUser(request);
            boardService.deleteBoard(user, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
        } catch (UserDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }catch (NotWriterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (BoardDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}/toggle-apply")
    @ApiOperation(value = "Board에 신청하기", notes = "로그인이 되어 있어야함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "신청할 수 없는 게시글"),
            @ApiResponse(code = 401, message = "로그인 되지 않음"),
            @ApiResponse(code = 404, message = "보드 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> toggleApply(HttpServletRequest request, @PathVariable int id) throws Exception{

        try {
            User user = userService.authUser(request);
            applicantService.toggleApply(boardService.getBoard(id).getBoard(), user.getId());
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (UserDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }catch (CanNotApplyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (BoardDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
