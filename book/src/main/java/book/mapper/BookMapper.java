package book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import book.dto.BookDto;

@Mapper
public interface BookMapper {
    List<BookDto> selectBookList();
    void insertBook(BookDto bookDto);
    BookDto selectBookDetail(int bookId);
    void updateBook(BookDto bookDto);
    void deleteBook(int bookId);
    List<BookDto> searchBooks(@Param("keyword") String keyword);
}
