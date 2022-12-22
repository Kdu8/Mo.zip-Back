package kdu8.mozip.presentation.dto;

import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.Category;
import kdu8.mozip.repository.ApplicantRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

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
