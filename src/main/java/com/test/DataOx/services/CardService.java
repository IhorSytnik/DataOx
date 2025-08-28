package com.test.DataOx.services;

import com.test.DataOx.model.Card;
import com.test.DataOx.repositories.CardJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardJpaRepository cardJpaRepository;
    @Autowired
    private DeckService deckService;

    public void create(String deckName, String question, String answer) {
        Card card = new Card(
                deckService.findByName(deckName),
                question,
                answer
        );
        cardJpaRepository.save(card);
    }

    public List<Card> listByDeckName(String deckName) {
        return deckService.findByName(deckName).getCards();
    }

    public void delete(Long id) {
        cardJpaRepository.deleteById(id);
    }

    public void edit(Long id, String newQuestion, String newAnswer) {
        Optional<Card> card = cardJpaRepository.findById(id);
        if (card.isEmpty())
            throw new NoSuchElementException("Couldn't find card with id " + id);
        card.get().setQuestion(newQuestion);
        card.get().setAnswer(newAnswer);
        cardJpaRepository.save(card.get());
    }

}
