package kdu8.mozip.repository;

import kdu8.mozip.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

    List<Applicant> findAllByBoardId(int id);

    List<Applicant> findAllByUserId(int id);

    @Transactional
    void deleteAllByBoardId(int id);

    boolean existsByBoardId(int id);

    int countAllByBoardId(int id);
}
