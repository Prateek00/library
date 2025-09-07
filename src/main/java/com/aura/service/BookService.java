package com.aura.service;

import com.aura.entity.Book;
import com.aura.model.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<BookModel> getAllBooks();
    BookModel getBookById(Long id);
    Book createBook(BookModel bookModel);
    Book updateBook(Long id, BookModel bookModel);
    void deleteBook(Long id);
    boolean validateBookInput(BookModel bookModel);
    boolean checkBookExists(Long id);
}
