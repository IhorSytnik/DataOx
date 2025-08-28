package com.test.DataOx.services;

import com.test.DataOx.model.Card;
import com.test.DataOx.model.Deck;
import com.test.DataOx.repositories.CardJpaRepository;
import com.test.DataOx.repositories.DeckJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class CardServiceTest {
    private Card testCard;
    private Deck testDeck;

    @Mock
    private CardJpaRepository cardJpaRepository;
    @Mock
    private DeckService deckService;
    @InjectMocks
    private CardService cardService;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        testCard = new Card();
        testCard.setQuestion("testQuestion");
        testCard.setAnswer("testAnswer");
        testDeck = new Deck("testDeck");
        testDeck.setCards(List.of(testCard));
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void givenDeckName_whenEditingNonExistentCard_thenThrowsException() {
        doReturn(Optional.empty()).when(cardJpaRepository).findById(11L);

        assertThrows(NoSuchElementException.class, () -> cardService.edit(11L,"newQuestion", "newAnswer"));
    }

    @Test
    void givenDeckName_whenProvidingCorrectName_thenListOfCardsIsReturned() {
        doReturn(testDeck).when(deckService).findByName("testDeck");

        List<Card> actual = cardService.listByDeckName("testDeck");

        assertEquals(List.of(testCard), actual);
    }

}