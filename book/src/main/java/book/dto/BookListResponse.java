package book.dto;

import lombok.Data;

@Data
public class BookListResponse {
    private int bookId;
    private String title;
    private String author;
    private String publishDate;
    private String createdAt;
}
