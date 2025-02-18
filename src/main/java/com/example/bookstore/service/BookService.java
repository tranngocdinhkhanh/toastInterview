package com.example.bookstore.service;

import com.example.bookstore.dto.request.CreateBookDto;
import com.example.bookstore.dto.request.UpdateBookDto;
import com.example.bookstore.dto.response.BookDetailDto;
import com.example.bookstore.dto.response.BookDto;
import com.example.bookstore.dto.response.PageDto;
import org.springframework.data.domain.Pageable;


public interface BookService {
    PageDto<BookDto> getAllBooks(Pageable pageable,String search, String filter);
    BookDetailDto getBookById(Long id);
    Long saveBook(CreateBookDto createBookDto);
    void deleteBook(Long id);
    UpdateBookDto updateBook(Long id, UpdateBookDto updateBookDto);

}
