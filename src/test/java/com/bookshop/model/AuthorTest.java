package com.bookshop.model;

import jakarta.persistence.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {
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
    void shouldCreateAuthor() {
        Author author = new Author();
        author.setName("David O.");
        assertNull(author.getId(), "Id should be null");

        tx.begin();
        em.persist(author);
        tx.commit();
        assertNotNull(author.getId(), "Id should not be null");

        assertNotNull(em.find(Author.class, author.getId()), "Author should have been persisted to DB");

        tx.begin();
        em.remove(author);
        tx.commit();

        assertNull(em.find(Author.class, author.getId()), "Author should have been removed from DB");
    }

    @Test
    void shouldNotCreateAuthorWithNullName() {
        Author author = new Author();
        author.setName("");
        assertThrows(NullPointerException.class, () -> author.setName(null));

        tx.begin();
        em.persist(author);

        assertThrows(RollbackException.class, () -> tx.commit());
    }

    @Test
    void shouldQueryAuthor() {
        assertEquals(0, em.createQuery("SELECT a FROM Author a").getResultList().size());

        Author david = new Author();
        david.setName("David O.");
        Author sofia = new Author();
        sofia.setName("Sofia M.");

        tx.begin();
        em.persist(david);
        em.persist(sofia);
        tx.commit();

        assertEquals(2, em.createQuery("SELECT a FROM Author a").getResultList().size());
        assertEquals(1, em.createQuery("SELECT a FROM Author a WHERE a.name = 'David O.'").getResultList().size());
    }
}
