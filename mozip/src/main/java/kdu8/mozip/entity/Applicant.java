package kdu8.mozip.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name = "applicant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int boardId;
    private int userId;
}
