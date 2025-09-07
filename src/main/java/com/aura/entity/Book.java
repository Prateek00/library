package com.aura.entity;


import com.aura.util.BookType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long bookId;

    @Column(name = "title", nullable = false)
    private String bookTitle;

    @Column(name = "author", nullable = false)
    private String bookAuthor;

    @Column(name = "price", nullable = false)
    private Double bookPrice;

    @Column(name = "type", nullable = false)
    private String bookType;


}
