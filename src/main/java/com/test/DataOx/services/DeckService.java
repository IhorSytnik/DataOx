package com.test.DataOx.services;

import com.test.DataOx.model.Deck;
import com.test.DataOx.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;

    public void create(String name) {
        deckRepository.save(new Deck(name));
    }

    public Iterable<Deck> list() {
        return deckRepository.findAll();
    }

    public void edit(String name, String newName) {
        Deck deck = deckRepository.findByName(name);
        deck.setName(newName);
        deckRepository.save(deck);
    }

    public void delete(String name) {
        deckRepository.deleteByName(name);
    }

    public Deck findByName(String name) {
        Deck deck = deckRepository.findByName(name);
        if (deck == null)
            throw new NoSuchElementException("There is no deck with name \"" + name + "\"");
        return deck;
    }
}
