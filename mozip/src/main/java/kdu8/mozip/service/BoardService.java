package kdu8.mozip.service;

import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.BoardRequest;
import kdu8.mozip.repository.ApplicantRepository;
import kdu8.mozip.repository.BoardRepository;
import kdu8.mozip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ApplicantRepository applicantRepository;

    private void checkExDate(Board board) {
        if (!board.isFinished() && board.getExDate().isAfter(LocalDateTime.now())) {
            board.setFinished(true);
            boardRepository.save(board);
        }
    }

    public Page<Board> getBoardListWithPage(@PageableDefault(size=10, sort="exDate",direction = Sort.Direction.DESC) Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board getBoard(int id) throws Exception {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (boardOptional.isPresent()) {
            Board board =  boardOptional.get();
            checkExDate(board);
            return board;
        }
        throw new Exception("보드 없음");
    }

    public Board createBoard(User user, BoardRequest boardRequest) {
        return boardRepository.save(Board.builder()
                        .title(boardRequest.getTitle())
                        .content(boardRequest.getContent())
                        .exDate(boardRequest.getExDate())
                        .category(boardRequest.getCategory())
                        .maxApp(boardRequest.getMaxApp())
                        .writerId(user.getId())
                        .build());
    }

    public Board checkBoard(Optional<Board> boardOptional, int userId) throws Exception {
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            if (board.getWriterId() == userId) {
                return board;
            }else {
                throw new Exception("같은 사용자가 아닙니다.");
            }
        } else {
            throw new Exception("글이 없습니다.");
        }
    }

    public Board updateBoard(User user, BoardRequest boardRequest, int boardId) throws Exception {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        Board board = checkBoard(boardOptional,user.getId());
        board.setCategory(boardRequest.getCategory());
        board.setContent(boardRequest.getContent());
        board.setTitle(boardRequest.getTitle());
        board.setMaxApp(boardRequest.getMaxApp());
        board.setExDate(boardRequest.getExDate());
        return boardRepository.save(board);
    }

    public void deleteBoard(User user, int boardId) throws Exception {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        Board board = checkBoard(boardOptional, user.getId());
        // 참조중인 applicant 테이블도 같이 삭제해야 함
        boardRepository.delete(board);
    }
}
