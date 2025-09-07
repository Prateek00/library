package com.aura.service;

import com.aura.entity.Book;
import com.aura.model.BookModel;
import com.aura.repository.BookRepository;
import com.aura.util.BookAssembler;
import com.aura.util.BookType;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final BookAssembler bookAssembler;

    public BookServiceImpl(BookRepository bookRepository, BookAssembler bookAssembler) {
        this.bookRepository = bookRepository;
        this.bookAssembler = bookAssembler;
    }

    @Override
    public List<BookModel> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(this.bookAssembler::getBookModel).toList();
    }

    @Override
    public BookModel getBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if(book != null){
            return bookAssembler.getBookModel(book);
        }
        return null;
    }

    @Override
    public Book createBook(BookModel bookModel) {
        Book book = bookAssembler.getBookEntity(bookModel);
        return this.bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, BookModel bookModel) {
        Book book = this.bookRepository.getReferenceById(id);
        book.setBookTitle(bookModel.getBookTitle());
        book.setBookAuthor(bookModel.getBookAuthor());
        book.setBookType(bookModel.getBookType().toString());
        book.setBookAuthor(bookModel.getBookAuthor());
        book.setBookPrice(bookModel.getBookPrice());
        return this.bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public boolean validateBookInput(BookModel bookModel) {
        return bookModel.getBookTitle() != null && !bookModel.getBookTitle().isEmpty()
                && bookModel.getBookAuthor() != null && !bookModel.getBookAuthor().isEmpty()
                && bookModel.getBookType() != null && isValidBookType(bookModel.getBookType())
                && bookModel.getBookPrice() != null
                && bookModel.getBookPrice() > 0;
    }

    @Override
    public boolean checkBookExists(Long id) {
        return this.bookRepository.existsById(id);
    }

    private boolean isValidBookType(BookType bookType) {
        for (BookType type : BookType.values()) {
            if (type == bookType) {
                return true;
            }
        }
        return false;
    }
}
