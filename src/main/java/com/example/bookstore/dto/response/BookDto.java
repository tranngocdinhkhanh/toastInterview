package com.example.bookstore.dto.response;

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
public class BookDto {

    private Long id;

    private String title;

    private Long authorId;

    private LocalDate publishedDate;

    private String isbn;

    private BigDecimal price;

}
