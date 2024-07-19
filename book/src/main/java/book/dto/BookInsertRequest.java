package book.dto;

import lombok.Data;

@Data
public class BookInsertRequest {
    private String title;
    private String author;
    private String publisher;
    private String publishDate;
    private String description;
}
