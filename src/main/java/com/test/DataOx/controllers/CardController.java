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

    @Command(command = "create", description = "Create a card for a specific deck.")
    public void create(String deckName, String question, String answer) {
        cardService.create(deckName, question, answer);
    }

    @Command(command = "list", description = "List all cards in a specific deck.")
    public Iterable<Card> list(String deckName) {
        return cardService.listByDeckName(deckName);
    }

    public void edit(Long id, String newQuestion, String newAnswer) {
        cardService.edit(id, newQuestion, newAnswer);
    }

    public void delete(String id) {
        cardService.delete(Long.valueOf(id));
    }
}
