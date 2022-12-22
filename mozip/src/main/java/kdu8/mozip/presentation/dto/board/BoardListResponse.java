package kdu8.mozip.presentation.dto.board;

import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.Category;
import kdu8.mozip.repository.ApplicantRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponse {

    private int id;
    private String title;
    private String content;
    private int writerId;
    private Category category;
    private int maxApp;
    private LocalDateTime exDate;
    private boolean finished;

    private int applicantCount;

    public static BoardListResponse getBoardListResponse(Board board, ApplicantRepository applicantRepository) {
        return BoardListResponse
                .builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerId(board.getWriterId())
                .category(board.getCategory())
                .maxApp(board.getMaxApp())
                .exDate(board.getExDate())
                .finished(board.isFinished())
                .applicantCount(applicantRepository.countAllByBoardId(board.getId()))
                .build();
    }
}
