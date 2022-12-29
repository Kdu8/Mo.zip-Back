package kdu8.mozip.repository;

import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findAllByWriterId(int writerId);

    Page<Board> findAllByCategory(Category category, Pageable pageable);
}
