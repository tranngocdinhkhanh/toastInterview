package com.example.bookstore.controller;

import com.example.bookstore.dto.request.CreateBookDto;
import com.example.bookstore.dto.request.UpdateBookDto;
import com.example.bookstore.dto.response.BookDetailDto;
import com.example.bookstore.dto.response.BookDto;
import com.example.bookstore.dto.response.PageDto;
import com.example.bookstore.dto.response.Response;
import com.example.bookstore.service.BookService;
import com.example.bookstore.utils.Message;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Response<Long>> createBook(@RequestBody @Valid CreateBookDto createBookDto) {
        Long savedBookId = bookService.saveBook(createBookDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBookId)
                .toUri();
        Response<Long> response = Response.<Long>builder()
                .code(Message.MSG_201.getCode())
                .description(Message.MSG_201.getDescription())
                .data(savedBookId)
                .build();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailDto> getBookById(@PathVariable Long id) {
        BookDetailDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);

    }

    @GetMapping
    public ResponseEntity<Response<PageDto<BookDto>>> getAllBooks(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String filter) {

        PageDto<BookDto> data = bookService.getAllBooks(PageRequest.of(page - 1, size), search,filter);
        Response<PageDto<BookDto>> response = new Response<>(
                Message.MSG_202.getCode(),
                Message.MSG_202.getDescription(),
                data
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UpdateBookDto>> updateBook(
            @PathVariable Long id,
            @RequestBody @Valid UpdateBookDto updateBookDto) {

        UpdateBookDto updatedBook = bookService.updateBook(id, updateBookDto);
        return ResponseEntity.ok(new Response<>(200, "Book updated successfully", updatedBook));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new Response<>(200, "Book deleted successfully", null));
    }
}
