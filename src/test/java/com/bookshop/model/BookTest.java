package com.bookshop.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("bookshop");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @AfterAll
    static void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    void shouldPersistBookWithTags() {
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(12.50));
        book.setIsbn("1-9754- 742-3");
        book.setNumOfPages(934);
        book.setTags(List.of("fantasy", "fun"));

        tx.begin();
        em.persist(book);
        tx.commit();

        assertNotNull(book.getId());
        List<Book> foundBoooks = em.createNamedQuery("findAllBooks", Book.class).getResultList();
        assertTrue(foundBoooks.size() >= 1);
    }

    @Test
    void shouldPersistBookWithChapters() {
        Book book = new Book();
        book.setTitle("Java EE 7");
        book.setPrice(BigDecimal.valueOf(25.50));
        book.setIsbn("1- 84023-742-4");
        book.setNumOfPages(504);
        book.setTags(List.of("java ee", "java", "enterprise"));
        book.setChapters(Map.of(1, new Chapter("Bean Validation"), 2, new Chapter("JPA"), 3, new Chapter("EJB")));

        tx.begin();
        em.persist(book);
        tx.commit();

        Book foundBoook = em.find(Book.class, book.getId());
        assertEquals(3, foundBoook.getTags().size());
        assertEquals(3, foundBoook.getChapters().size());
    }

    @Test
    void shouldPersistBookWithAuthors() {
        Book book = new Book();
        book.setTitle("C++ for complete idiots");
        book.setPrice(BigDecimal.valueOf(22.50));
        book.setIsbn("5-84023-742-5");
        book.setNumOfPages(345);
        book.setAuthors(Set.of(new Author("Sofia Troina"), new Author("Enrico Carlini")));

        tx.begin();
        em.persist(book);
        tx.commit();

        Book foundBoook = em.find(Book.class, book.getId());
        assertEquals(2, foundBoook.getAuthors().size());
    }
}
