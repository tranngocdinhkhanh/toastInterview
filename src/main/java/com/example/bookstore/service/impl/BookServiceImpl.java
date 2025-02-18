package com.example.bookstore.service.impl;

import com.example.bookstore.dto.request.CreateBookDto;
import com.example.bookstore.dto.request.UpdateBookDto;
import com.example.bookstore.dto.response.BookDetailDto;
import com.example.bookstore.dto.response.BookDto;
import com.example.bookstore.dto.response.PageDto;
import com.example.bookstore.exception.common.DuplicateException;
import com.example.bookstore.exception.common.NotFoundException;
import com.example.bookstore.modal.Author;
import com.example.bookstore.modal.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import com.example.bookstore.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public PageDto<BookDto> getAllBooks(Pageable pageable, String search,String filter) {
        Page<Book> page = switch (filter) {
            case "author_id" -> {
                Long authorId = Long.parseLong(search);
                yield bookRepository.findByAuthorId(authorId, pageable);
            }
            case "published_date" -> {
                LocalDate publishedDate = LocalDate.parse(search);
                yield bookRepository.findByPublishedDateAfter(publishedDate, pageable);
            }
            case "title" -> bookRepository.findByTitleContainingIgnoreCase(search, pageable);
            case "min_price" -> {
                BigDecimal minPrice = new BigDecimal(search);
                yield bookRepository.findByPriceGreaterThanEqual(minPrice, pageable);
            }
            case "max_price" -> {
                BigDecimal maxPrice = new BigDecimal(search);
                yield bookRepository.findByPriceLessThanEqual(maxPrice, pageable);
            }
            default -> bookRepository.findAll(pageable);
        };


        List<BookDto> bookDtoList = page.getContent().stream()
                .map(book -> new BookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor().getId(),
                        book.getPublishedDate(),
                        book.getIsbn(),
                        book.getPrice()
                ))
                .toList();
        PageDto<BookDto> pageDto = new PageDto<>();
        pageDto.setData(bookDtoList);
        pageDto.setPage(pageable.getPageNumber() + 1);
        pageDto.setSize(pageable.getPageSize());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setTotalElements(page.getTotalElements());
        return pageDto;
    }


    @Override
    public BookDetailDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.MSG_103, id));
        return new BookDetailDto(book);
    }

    @Override
    public Long saveBook(CreateBookDto bookDto) {
        if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
            throw new DuplicateException(Message.MSG_410, bookDto.getIsbn());
        }
        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(Message.MSG_103, bookDto.getAuthorId()));
        Book book = bookDto.toBook();
        book.setAuthor(author);
        bookRepository.save(book);
        return book.getId();
    }

    public UpdateBookDto updateBook(Long id, UpdateBookDto updateBookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.MSG_103, id));

        Author author = authorRepository.findById(updateBookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(Message.MSG_103, updateBookDto.getAuthorId()));
        boolean isIsbnExists = bookRepository.existsByIsbnAndIdNot(updateBookDto.getIsbn(), id);
        if (isIsbnExists) {
            throw new DuplicateException(Message.MSG_410,updateBookDto.getIsbn());
        }

        book.setTitle(updateBookDto.getTitle());
        book.setPrice(updateBookDto.getPrice());
        book.setPublishedDate(updateBookDto.getPublishedDate());
        book.setAuthor(author);
        book.setIsbn(updateBookDto.getIsbn());


        Book updatedBook = bookRepository.save(book);


        return new UpdateBookDto(
                updatedBook.getTitle(),
                updatedBook.getPrice(),
                updatedBook.getPublishedDate(),
                updatedBook.getAuthor().getId(),
                updatedBook.getIsbn()
        );
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException(Message.MSG_103, id);
        }
        bookRepository.deleteById(id);
    }

}
