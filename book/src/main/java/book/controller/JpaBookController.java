package book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import book.entity.BookEntity;
import book.service.JpaBookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/jpa")
public class JpaBookController {
    
    @Autowired
    private JpaBookService jpaBookService;
    
    @GetMapping("/book")
    public ModelAndView openBookList(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("/book/jpaBookList");
        
        List<BookEntity> list = jpaBookService.selectBookList();
        mv.addObject("list", list);
        
        return mv;
    }
    
    @GetMapping("/book/write")
    public String openBookWrite() throws Exception {
        return "/book/jpaBookWrite";
    }
    
    @PostMapping("/book/write")
    public String insertBook(BookEntity bookEntity) throws Exception {
        jpaBookService.saveBook(bookEntity);
        return "redirect:/jpa/book";    
    }
    
    @GetMapping("/book/{bookId}")
    public ModelAndView openBookDetail(@PathVariable("bookId") int bookId) throws Exception {
        ModelAndView mv = new ModelAndView("/book/jpaBookDetail");
        
        BookEntity bookEntity = jpaBookService.selectBookDetail(bookId);
        mv.addObject("book", bookEntity);
        
        return mv;
    }
    
    @PutMapping("/book/{bookId}")
    public String updateBook(@PathVariable("bookId") int bookId, BookEntity bookEntity) throws Exception {
        bookEntity.setBookId(bookId);
        jpaBookService.saveBook(bookEntity);
        return "redirect:/jpa/book";
    }
    
    @DeleteMapping("/book/{bookId}")
    public String deleteBook(@PathVariable("bookId") int bookId) throws Exception {
        jpaBookService.deleteBook(bookId);
        return "redirect:/jpa/book";
    }
}
