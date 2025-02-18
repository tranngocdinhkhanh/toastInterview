package com.example.bookstore.dto.response;

import com.example.bookstore.modal.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailDto {

    private Long id;
    private String title;
    private Long authorId;
    private LocalDate publishedDate;
    private String isbn;
    private BigDecimal price;
    private String nameAuthor;
    private String nationalityAuthor;

    public BookDetailDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.publishedDate = book.getPublishedDate();
        this.isbn = book.getIsbn();
        this.price = book.getPrice();
        this.authorId = book.getAuthor().getId();
        this.nameAuthor = book.getAuthor().getName();
        this.nationalityAuthor = book.getAuthor().getNationality();
    }
}
