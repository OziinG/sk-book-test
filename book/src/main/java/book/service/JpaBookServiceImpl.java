package book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import book.entity.BookEntity;
import book.repository.JpaBookRepository;

@Service
public class JpaBookServiceImpl implements JpaBookService {

    @Autowired
    private JpaBookRepository jpaBookRepository;

    @Override
    public List<BookEntity> selectBookList() {
        return jpaBookRepository.findAllByOrderByBookIdDesc();
    }

    @Override
    public void saveBook(BookEntity bookEntity) throws Exception {
        // TODO. 로그인한 사용자 계정으로 변경
        bookEntity.setCreatorId("tester");

        // 리포지터의 save 메서드는 insert와 update 두 가지 역할을 수행 
        jpaBookRepository.save(bookEntity);
    }

    @Override
    public void deleteBook(int bookId) {
        jpaBookRepository.deleteById(bookId);
    }

    @Override
    public BookEntity selectBookDetail(int bookId) throws Exception {
        Optional<BookEntity> optional = jpaBookRepository.findById(bookId);
        if (optional.isPresent()) {
            BookEntity bookEntity = optional.get();
            return bookEntity;
        } else {
            throw new Exception("일치하는 데이터가 없음");
        }
    }
}
