package com.bookshop.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    Customer customer = new Customer("George", "Bush", "bush@domain.com");
    Address address = new Address("Via Natale Palli 22", "Torino", "Italy");

    @BeforeAll
    public static void init() {
        emf = Persistence.createEntityManagerFactory("bookshop");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @AfterAll
    public static void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @BeforeEach
    public void setup() {
        customer.setAddresses(List.of(address));
    }


    @Test
    public void shouldCreateAndDeleteCustomer() {
        tx.begin();
        em.persist(customer);
        tx.commit();

        assertNotNull(customer.getId());

        tx.begin();
        em.remove(customer);
        tx.commit();

        assertEquals("George", customer.getFirstName(), "Customer should still be available until garbage collection");

        Customer foundCustomer = em.find(Customer.class, customer.getId());
        assertNull(foundCustomer);

//        assertThrows(IllegalStateException.class, () -> {
//            tx.begin();
//            em.persist(customer);
//            em.flush();
//        });
    }

    @Test
    public void shouldRefreshCustomerFromDB() {
        tx.begin();
        em.persist(customer);
        tx.commit();

        Customer foundCustomer = em.find(Customer.class, customer.getId());
        assertEquals("George", customer.getFirstName());

        foundCustomer.setFirstName("John");
        assertEquals("John", customer.getFirstName());

        em.refresh(foundCustomer);
        assertEquals("George", customer.getFirstName());
    }

    @Test
    public void shouldContainCustomerInPersistenceContext() {
        assertFalse(em.contains(customer));

        tx.begin();
        em.persist(customer);
        tx.commit();

        assertTrue(em.contains(customer));

        em.detach(customer);

        assertFalse(em.contains(customer));
//
//        tx.begin();
//        em.remove(customer);
//        tx.commit();
//
//        assertFalse(em.contains(customer));
    }

    @Test
    public void shouldClearPersistenceContextAndMergeCustomer() {
        tx.begin();
        em.persist(customer);
        tx.commit();
        assertTrue(em.contains(customer));

        em.clear();
        assertFalse(em.contains(customer));

        customer.setFirstName("John");
        tx.begin();
        Customer mergedCustomer = em.merge(customer);
        tx.commit();
        assertTrue(em.contains(mergedCustomer));

        em.clear();
        assertFalse(em.contains(mergedCustomer));

        Customer foundCustomer = em.find(Customer.class, customer.getId());
        assertEquals("John", customer.getFirstName());
        assertTrue(em.contains(foundCustomer));
    }

    @Test
    public void shouldUpdateCustomer() {
        tx.begin();
        em.persist(customer);
        assertEquals("George", customer.getFirstName());

        customer.setFirstName("John");
        assertEquals("John", customer.getFirstName());

        tx.commit();

        Customer foundCustomer = em.find(Customer.class, customer.getId());
        assertEquals("John", foundCustomer.getFirstName());
    }
}
