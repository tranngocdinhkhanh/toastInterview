package com.example.bookstore;

import com.example.bookstore.modal.Author;
import com.example.bookstore.modal.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("com.example.bookstore.repository")
@EntityScan("com.example.bookstore.modal")
public class ToastApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToastApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            if (authorRepository.count() == 0) {
                Author author1 = new Author();
                author1.setName("J.K. Rowling");
                author1.setNationality("British");

                Author author2 = new Author();
                author2.setName("George Orwell");
                author2.setNationality("British");

                authorRepository.saveAll(List.of(author1, author2));
            }

            if (bookRepository.count() == 0) {
                List<Author> authors = authorRepository.findAll();

                Book book1 = new Book();
                book1.setTitle("Harry Potter and the Sorcerer's Stone");
                book1.setAuthor(authors.get(0)); // J.K. Rowling
                book1.setPublishedDate(LocalDate.of(1997, 6, 26));
                book1.setIsbn("9780747532743");
                book1.setPrice(new BigDecimal("20.99"));

                Book book2 = new Book();
                book2.setTitle("1984");
                book2.setAuthor(authors.get(1)); // George Orwell
                book2.setPublishedDate(LocalDate.of(1949, 6, 8));
                book2.setIsbn("9780451524935");
                book2.setPrice(new BigDecimal("15.99"));

                bookRepository.saveAll(List.of(book1, book2));
            }
        };
    }
}
