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
    private int userId;
    private String verifyCode;
}
