package kdu8.mozip.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Builder
@Table(name = "verify_code")
public class VerifyCode {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "verify_code")
    private String verifyCode;
}
