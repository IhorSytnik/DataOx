package com.test.DataOx.services;

import com.test.DataOx.model.Deck;
import com.test.DataOx.repositories.DeckJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DeckService {
    @Autowired
    private DeckJpaRepository deckJpaRepository;

    public void create(String name) {
        deckJpaRepository.save(new Deck(name));
    }

    public Iterable<Deck> list() {
        return deckJpaRepository.findAll();
    }

    public void edit(String name, String newName) {
        Deck deck = findByName(name);
        deck.setName(newName);
        deckJpaRepository.save(deck);
    }

    public void delete(String name) {
        deckJpaRepository.deleteByName(name);
    }

    public Deck findByName(String name) {
        Optional<Deck> deck = deckJpaRepository.findByName(name);
        if (deck.isEmpty())
            throw new NoSuchElementException("There is no deck with name \"" + name + "\"");
        return deck.get();
    }
}
