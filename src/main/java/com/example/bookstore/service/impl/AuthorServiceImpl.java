package com.example.bookstore.service.impl;

import com.example.bookstore.exception.common.AuthorHasBooksException;
import com.example.bookstore.exception.common.NotFoundException;
import com.example.bookstore.modal.Author;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.AuthorService;
import com.example.bookstore.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {


    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    public void deleteAuthor(Long id) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.MSG_103, id));

        boolean hasBooks = bookRepository.existsByAuthorId(id);
        if (hasBooks) {
            throw new AuthorHasBooksException(Message.MSG_411, id);
        }

        authorRepository.delete(author);
    }
}
