package kdu8.mozip.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private int writerId;
    @Enumerated
    private Category category;
    private int maxApp;
    private LocalDateTime exDate;
    @ColumnDefault("false")
    private boolean finished;
}

