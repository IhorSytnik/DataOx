package com.test.DataOx.repositories;

import com.test.DataOx.model.Card;
import com.test.DataOx.model.Deck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class DeckJpaRepositoryTest {
    @Autowired
    private DeckJpaRepository deckJpaRepository;
    @Autowired
    private CardJpaRepository cardJpaRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Deck testDeck;
    private Card testCard;

    @BeforeEach
    public void setUp() {
        // Initialize test data before each test method
        testDeck = new Deck();
        testDeck.setName("testDeck");
        deckJpaRepository.save(testDeck);

        testCard = new Card();
        testCard.setQuestion("testQuestion");
        testCard.setAnswer("testAnswer");
        testCard.setDeck(testDeck);
        cardJpaRepository.save(testCard);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @AfterEach
    public void tearDown() {
        // Release test data after each test method
        deckJpaRepository.delete(testDeck);
        cardJpaRepository.delete(testCard);
    }

    @Test
    void givenDeck_whenSaved_thenCanBeFoundById() {
        Deck savedDeck = deckJpaRepository.findById(testDeck.getId()).orElse(null);
        assertNotNull(savedDeck);
        assertEquals(testDeck.getName(), savedDeck.getName());
        assertIterableEquals(List.of(testCard), savedDeck.getCards());
    }

    @Test
    void givenDeck_whenSaved_thenCanBeFoundByName() {
        Deck savedDeck = deckJpaRepository.findByName(testDeck.getName()).orElse(null);
        assertNotNull(savedDeck);
        assertEquals(testDeck.getName(), savedDeck.getName());
    }

    @Test
    void givenDeck_whenSaved_thenCanBeDeletedById() {
        deckJpaRepository.deleteById(testDeck.getId());
        Deck savedDeck = deckJpaRepository.findById(testDeck.getId()).orElse(null);
        assertNull(savedDeck);
    }

    @Test
    void givenDeck_whenSaved_thenCanBeDeletedByName() {
        deckJpaRepository.deleteByName(testDeck.getName());
        Deck savedDeck = deckJpaRepository.findById(testDeck.getId()).orElse(null);
        assertNull(savedDeck);
    }

    @Test
    void givenDeck_whenDeleted_thenCardsAreDeletedAsWell() {
        deckJpaRepository.deleteById(testDeck.getId());
        assertThrows(JpaObjectRetrievalFailureException.class, () -> cardJpaRepository.getReferenceById(testCard.getId()));
    }
}