package kdu8.mozip.repository;

import kdu8.mozip.entity.VerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyCodeRepository extends JpaRepository<VerifyCode, Integer> {
    Optional<VerifyCode> findByVerifyCode(String verifyCode);
}
