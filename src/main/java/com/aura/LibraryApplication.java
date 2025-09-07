package com.aura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {
    public static void main(String[] args) {
        System.out.println("Library Application Started...");
        SpringApplication.run(LibraryApplication.class, args);

    }
}
