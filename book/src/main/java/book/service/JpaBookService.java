package book.service;

import java.util.List;

import book.entity.BookEntity;

public interface JpaBookService {
	List<BookEntity> selectBookList();

	void saveBook(BookEntity bookEntity) throws Exception;

	void deleteBook(int bookId);

	BookEntity selectBookDetail(int bookId) throws Exception;
}
