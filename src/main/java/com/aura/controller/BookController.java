package com.aura.controller;

import com.aura.entity.Book;
import com.aura.model.BookModel;
import com.aura.service.BookService;
import com.aura.util.CreateBookResourceModel;
import com.aura.util.DeleteBookResourceModel;
import com.aura.util.UpdateBookResourceModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @Operation(summary = "Create a new book", description = "Creates a new book with the provided details.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid book data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    public ResponseEntity<CreateBookResourceModel> createBook(@Parameter(in = ParameterIn.DEFAULT, description = "create a new book", required = true,
                                        schema = @io.swagger.v3.oas.annotations.media.Schema())
                                        @RequestBody BookModel bookModel){
        log.info("Creating a new book");
        boolean isBookInputValid =  bookService.validateBookInput(bookModel);
        if(!isBookInputValid){
            log.error("Invalid book data provided");
            return ResponseEntity.badRequest().build();
        }
        Book book = bookService.createBook(bookModel);
        URI location = linkTo(methodOn(BookController.class).getBookById(book.getBookId())).withSelfRel().toUri();
        return ResponseEntity.created(location).body(CreateBookResourceModel.builder().location(location.toString()).id(book.getBookId()).build());
    }

    @Operation(summary = "Get book by ID", description = "Retrieves a book by its ID.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookModel> getBookById(@Parameter(in = ParameterIn.PATH, description = "ID of the book to retrieve", required = true,
                                        schema = @io.swagger.v3.oas.annotations.media.Schema())
                                        @PathVariable("id") Long id) {
        log.info("Fetching book with ID: {}", id);
        BookModel bookModel = bookService.getBookById(id);
        if (bookModel == null) {
            log.error("Book with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookModel);
    }

    @Operation(summary = "Get paginated list of books", description = "Retrieves all books with pagination support.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<BookModel>> getAllBooks() {
        List<BookModel> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @Operation(summary = "Update an existing book", description = "Updates the details of an existing book by its ID.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid book data"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UpdateBookResourceModel> updateBook(@Parameter(in = ParameterIn.PATH, description = "ID of the book to update", required = true,
                                        schema = @io.swagger.v3.oas.annotations.media.Schema())
                                        @PathVariable Long id,
                                        @Parameter(in = ParameterIn.DEFAULT, description = "Updated book details", required = true,
                                        schema = @io.swagger.v3.oas.annotations.media.Schema())
                                        @RequestBody BookModel bookModel) {
        log.info("Updating book with ID: {}", id);
        if (bookService.checkBookExists(id)) {
            log.error("Book with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        boolean isBookInputValid = bookService.validateBookInput(bookModel);
        if (!isBookInputValid) {
            log.error("Invalid book data provided for ID: {}", id);
            return ResponseEntity.badRequest().build();
        }
        Book book = bookService.updateBook(id, bookModel);
        URI location = linkTo(methodOn(BookController.class).getBookById(book.getBookId())).toUri();
        return ResponseEntity.created(location).body(UpdateBookResourceModel.builder().location(location.toString()).id(book.getBookId()).build());
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a book", description = "Deletes a book by its ID.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DeleteBookResourceModel> deleteBook(@Parameter(in = ParameterIn.PATH, description = "ID of the book to delete", required = true,
                                        schema = @io.swagger.v3.oas.annotations.media.Schema())
                                        @PathVariable Long id) {
        log.info("Deleting book with ID: {}", id);
        if (!bookService.checkBookExists(id)) {
            log.error("Book with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(id);
        DeleteBookResourceModel response = DeleteBookResourceModel.builder()
                .id(id)
                .message("Book deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}
