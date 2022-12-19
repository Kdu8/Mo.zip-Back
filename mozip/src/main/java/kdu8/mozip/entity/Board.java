package kdu8.mozip.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writer_id")
    private int writerId;

    @Enumerated
    @Column(name = "category")
    private Category category;

    @Column(name = "ex_date")
    private LocalDateTime exDate;

    @Column(name = "finished")
    private boolean finished;
}

enum Category {

    Sports, Project, Purchase

}
