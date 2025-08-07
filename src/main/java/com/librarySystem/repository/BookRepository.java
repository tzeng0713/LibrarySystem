package com.librarySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.librarySystem.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
