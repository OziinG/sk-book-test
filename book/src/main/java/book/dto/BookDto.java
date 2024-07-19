package book.dto;

import lombok.Data;

@Data
public class BookDto {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String publishDate;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String creatorId;
    private String updatorId;
}
