package com.test.DataOx.controllers;

import com.test.DataOx.model.Card;
import com.test.DataOx.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.standard.AbstractShellComponent;

@Command(group = "Card commands", command = "card")
public class CardController extends AbstractShellComponent {

    @Autowired
    private CardService cardService;
    @Autowired
    private ComponentFlow.Builder componentFlowBuilder;

    /**
     * Creates new card in the provided deck.
     *
     * @param deckName name of the deck to put the card into.
     * @param question question text for the card.
     * @param answer answer text for the card.
     */
    @Command(command = "create", description = "Create a card for a specific deck.")
    public void create(String deckName, String question, String answer) {
        cardService.create(deckName, question, answer);
    }

    /**
     * Get the list of cards from the specified deck.
     *
     * @param deckName name of the deck.
     * @return list of cards in the deck.
     */
    @Command(command = "list", description = "List all cards in a specific deck.")
    public Iterable<Card> list(String deckName) {
        return cardService.listByDeckName(deckName);
    }

    /**
     * Changes question and answer of the card with the provided id.
     * Not a CLI command.
     *
     * @param id card id.
     * @param newQuestion new question text for the card.
     * @param newAnswer new answer text for the card.
     */
    public void edit(Long id, String newQuestion, String newAnswer) {
        cardService.edit(id, newQuestion, newAnswer);
    }

    /**
     * Deletes the card with the provided id.
     * Not a CLI command.
     *
     * @param id id of the card to be deleted.
     */
    public void delete(String id) {
        cardService.delete(Long.valueOf(id));
    }
}
