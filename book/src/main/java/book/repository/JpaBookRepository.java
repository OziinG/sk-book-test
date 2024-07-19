package book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import book.entity.BookEntity;

@Repository
public interface JpaBookRepository extends JpaRepository<BookEntity, Integer> {
    // bookId를 내림차순으로 정렬한 데이터를 조회
    List<BookEntity> findAllByOrderByBookIdDesc();
}
