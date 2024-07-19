package book.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import book.dto.BookDto;
import book.dto.BookInsertRequest;
import book.dto.BookListResponse;
import book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/book")
@Tag(name = "rest-api-book-controller", description = "REST API for managing books with Spring Boot")
public class RestApiBookController {
    
    @Autowired
    private BookService bookService;

    @Operation(summary = "도서 목록 조회", description = "등록된 도서 목록을 조회해서 반환합니다.")
    @GetMapping
    public List<BookListResponse> openBookList(HttpServletRequest request) throws Exception {
        List<BookDto> bookList = bookService.selectBookList();
        List<BookListResponse> results = new ArrayList<>();
        bookList.forEach(dto -> {
            results.add(new ModelMapper().map(dto, BookListResponse.class));
        });
        return results;
    }

    @Operation(summary = "도서 등록", description = "도서 정보를 저장합니다.")
    @Parameter(name = "bookInsertRequest", description = "도서 정보를 담고 있는 객체", required = true)
    @PostMapping("/write")
    public ResponseEntity<Map<String, String>> insertBook(@RequestBody BookInsertRequest bookInsertRequest) {
        Map<String, String> response = new HashMap<>();

        if (!StringUtils.hasText(bookInsertRequest.getTitle()) || !StringUtils.hasText(bookInsertRequest.getAuthor())) {
            response.put("error", "제목과 저자는 필수 입력 항목입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            BookDto bookDto = new ModelMapper().map(bookInsertRequest, BookDto.class);
            bookService.insertBook(bookDto);
            response.put("message", "도서가 성공적으로 등록되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("도서 등록 중 오류 발생", e);
            response.put("error", "서버 오류가 발생했습니다. 다시 시도해주세요.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "도서 상세 조회", description = "ID로 도서 정보를 조회합니다.")
    @Parameter(name = "bookId", description = "조회할 도서의 ID", required = true)
    @GetMapping("/{bookId}")
    public ResponseEntity<Object> openBookDetail(@PathVariable("bookId") int bookId) throws Exception {
        BookDto bookDto = bookService.selectBookDetail(bookId);
        if (bookDto == null) {
            Map<String, String> result = new HashMap<>();
            result.put("code", HttpStatus.NOT_FOUND.toString());
            result.put("message", "일치하는 도서가 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(bookDto);
        }
    }

    @Operation(summary = "도서 수정", description = "ID로 도서 정보를 수정합니다.")
    @Parameter(name = "bookId", description = "수정할 도서의 ID", required = true)
    @Parameter(name = "bookDto", description = "수정할 도서 정보 객체", required = true)
    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(@PathVariable("bookId") int bookId, @RequestBody BookDto bookDto) throws Exception {
        bookDto.setBookId(bookId);
        bookService.updateBook(bookDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "도서 삭제", description = "ID로 도서를 삭제합니다.")
    @Parameter(name = "bookId", description = "삭제할 도서의 ID", required = true)
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") int bookId) throws Exception {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

 
}
