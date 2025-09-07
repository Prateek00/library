package com.aura.util;

import com.aura.entity.Book;
import com.aura.model.BookModel;
import org.springframework.stereotype.Component;

@Component
public class BookAssembler {

    public BookModel getBookModel(Book book) {
        BookModel bookModel = new BookModel();
        bookModel.setBookId(book.getBookId());
        bookModel.setBookTitle(book.getBookTitle() != null ? book.getBookTitle() : "");
        bookModel.setBookAuthor(book.getBookAuthor() != null ? book.getBookAuthor() : "");
        bookModel.setBookType(BookType.valueOf(book.getBookType()));
        bookModel.setBookPrice(book.getBookPrice());
        return bookModel;
    }

    public Book getBookEntity(BookModel bookModel) {
        Book book = new Book();
        book.setBookTitle(bookModel.getBookTitle());
        book.setBookAuthor(bookModel.getBookAuthor());
        book.setBookType(bookModel.getBookType().toString());
        book.setBookPrice(bookModel.getBookPrice());
        return book;
    }
}
