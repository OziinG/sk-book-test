package book.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import book.dto.BookDto;
import book.dto.BookInsertRequest;
import book.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookController {
    
    private BookService bookService;

    @GetMapping("/book/openBookList.do")
    public ModelAndView openBookList(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("/book/bookList");
        
        List<BookDto> list = bookService.selectBookList();
        mv.addObject("list", list);
        
        return mv;
    }
    
    @GetMapping("/book/openBookWrite.do")
    public String openBookWrite() throws Exception {
        return "/book/bookWrite";
    }
        
    @PostMapping("/book/insertBook.do")
    public String insertBook(BookInsertRequest bookInsertRequest) throws Exception {
        // 서비스 메서드에 맞춰서 데이터를 변경
        BookDto bookDto = new ModelMapper().map(bookInsertRequest, BookDto.class);
        
        bookService.insertBook(bookDto);
        return "redirect:/book/openBookList.do";    
    }
    
    @GetMapping("/book/openBookDetail.do")
    public ModelAndView openBookDetail(@RequestParam("bookId") int bookId) throws Exception {
        ModelAndView mv = new ModelAndView("/book/bookDetail");
        
        BookDto bookDto = bookService.selectBookDetail(bookId);
        mv.addObject("book", bookDto);
        
        return mv;
    }
    
    @PostMapping("/book/updateBook.do")
    public String updateBook(BookDto bookDto) throws Exception {
        bookService.updateBook(bookDto);
        return "redirect:/book/openBookList.do";
    }
    
    @PostMapping("/book/deleteBook.do")
    public String deleteBook(@RequestParam("bookId") int bookId) throws Exception {
        bookService.deleteBook(bookId);
        return "redirect:/book/openBookList.do";
    }
}
