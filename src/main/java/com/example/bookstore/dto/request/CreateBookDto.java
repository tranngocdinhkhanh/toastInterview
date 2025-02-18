package com.example.bookstore.dto.request;

import com.example.bookstore.modal.Book;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBookDto {
    @NotBlank(message = "Tittle of book cannot be blank")
    private String title;
    @NotNull(message = "Author's id of book cannot be null")
    @Min(value = 1, message = "Author's id of book must be greater than or equal to 1")
    private Long authorId;
    @NotNull(message = "Published date of book cannot be null")
    private LocalDate publishedDate;
    @NotBlank(message = "Isbn of book cannot be null")
    private String isbn;
    @NotNull(message = "Price of book cannot be null")
    private BigDecimal price;

    @AssertTrue(message = "Published date must be before the current date")
    public boolean isPublishedDateValid() {
        return publishedDate != null && publishedDate.isBefore(LocalDate.now());
    }

    @AssertTrue(message = "Price must be greater than 0")
    public boolean isPriceValid() {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    public Book toBook() {
        Book book = new Book();
        book.setTitle(title);
        book.setPublishedDate(publishedDate);
        book.setIsbn(isbn);
        book.setPrice(price);
        return book;
    }
}
