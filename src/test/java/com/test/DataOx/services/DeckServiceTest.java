package com.test.DataOx.services;

import com.test.DataOx.exceptions.ElementAlreadyExistsException;
import com.test.DataOx.model.Deck;
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

class DeckServiceTest {
    private Deck testDeck;

    @Mock
    private DeckJpaRepository deckJpaRepository;
    @InjectMocks
    private DeckService deckService;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        testDeck = new Deck("testDeck");
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void givenDeck_whenProvidedCorrectName_thenCanBeFound() {
        doReturn(Optional.of(testDeck)).when(deckJpaRepository).findByName("testDeck");
        Deck actual = deckService.findByName("testDeck");

        assertEquals(testDeck, actual);
    }

    @Test
    void givenDeck_whenProvidedIncorrectName_thenThrowsException() {
        doReturn(Optional.empty()).when(deckJpaRepository).findByName("testDeck");

        assertThrows(NoSuchElementException.class, () -> deckService.findByName("testDeck"));
    }

    @Test
    void givenDeck_whenGettingListOfDecks_thenAcquireList() {
        doReturn(List.of(testDeck)).when(deckJpaRepository).findAll();

        List<Deck> actual = (List<Deck>) deckService.list();

        assertEquals(List.of(testDeck), actual);
    }

    @Test
    void givenNoDecks_whenGettingListOfDecks_thenAcquireEmptyList() {
        doReturn(List.of()).when(deckJpaRepository).findAll();

        List<Deck> actual = (List<Deck>) deckService.list();

        assertEquals(List.of(), actual);
    }

    @Test
    void givenDeck_whenProvidedDuplicateDeckName_thenThrowsException() {
        doReturn(Optional.of(testDeck)).when(deckJpaRepository).findByName("testDeck");

        assertThrows(ElementAlreadyExistsException.class, () -> deckService.create("testDeck"));
    }
}