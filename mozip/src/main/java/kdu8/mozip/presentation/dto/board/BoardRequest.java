package kdu8.mozip.presentation.dto.board;

import kdu8.mozip.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {
    private String title;
    private String content;
    private Category category;
    private int maxApp;
    private LocalDateTime exDate;
    private String requirement;
}
