package com.example.bookstore.repository;

import com.example.bookstore.modal.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    boolean existsByAuthorId(Long authorId);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    Page<Book> findByAuthorId(Long authorId, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByPublishedDateAfter(LocalDate publishedDate, Pageable pageable);

    Page<Book> findByPriceGreaterThanEqual(BigDecimal price, Pageable pageable);

    Page<Book> findByPriceLessThanEqual(BigDecimal price, Pageable pageable);
}
