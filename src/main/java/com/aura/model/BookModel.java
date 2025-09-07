package com.aura.model;

import com.aura.util.BookType;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {
    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private Double bookPrice;
    private BookType bookType;
}
