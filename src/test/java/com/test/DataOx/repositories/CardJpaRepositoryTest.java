package com.test.DataOx.repositories;

import com.test.DataOx.model.Card;
import com.test.DataOx.model.Deck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CardJpaRepositoryTest {
    @Autowired
    private CardJpaRepository cardJpaRepository;

    private Card testCard;

    @BeforeEach
    public void setUp() {
        // Initialize test data before each test method
        testCard = new Card();
        testCard.setQuestion("testQuestion");
        testCard.setAnswer("testAnswer");
        cardJpaRepository.save(testCard);
    }

    @AfterEach
    public void tearDown() {
        // Release test data after each test method
        cardJpaRepository.delete(testCard);
    }

    @Test
    void givenCard_whenSaved_thenCanBeFoundById() {
        Card savedCard = cardJpaRepository.findById(testCard.getId()).orElse(null);
        assertNotNull(savedCard);
        assertEquals(testCard.getQuestion(), savedCard.getQuestion());
        assertEquals(testCard.getAnswer(), savedCard.getAnswer());
    }

    @Test
    void givenCard_whenSaved_thenCanBeDeletedById() {
        cardJpaRepository.deleteById(testCard.getId());
        Card savedCard = cardJpaRepository.findById(testCard.getId()).orElse(null);
        assertNull(savedCard);
    }

}