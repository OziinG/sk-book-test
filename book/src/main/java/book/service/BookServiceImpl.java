package book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import book.dto.BookDto;
import book.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;
    
    @Override
    public List<BookDto> selectBookList() {
        return bookMapper.selectBookList();
    }

    @Override
    public void insertBook(BookDto bookDto) throws Exception {
        // TODO. 로그인한 사용자의 ID로 변경
        bookDto.setCreatorId("hong");
        bookMapper.insertBook(bookDto);
    }

    @Override
    public BookDto selectBookDetail(int bookId) {
        return bookMapper.selectBookDetail(bookId);
    }

    @Override
    public void updateBook(BookDto bookDto) {
        // TODO. 로그인한 사용자 아이디로 변경
        bookDto.setUpdatorId("go");
        bookMapper.updateBook(bookDto);
    }

    @Override
    public void deleteBook(int bookId) {
        bookMapper.deleteBook(bookId);
    }
}
