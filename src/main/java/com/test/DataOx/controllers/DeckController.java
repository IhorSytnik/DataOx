package com.test.DataOx.controllers;

import com.test.DataOx.model.Deck;
import com.test.DataOx.services.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

@Command(group = "Deck commands", command = "deck")
public class DeckControllerImpl {
    @Autowired
    private DeckService deckService;

    @Command(command = "create", description = "Create a new deck with a given name.")
    public void create(String name) {
        deckService.create(name);
    }

    @Command(command = "list", description = "List all decks.")
    public Iterable<Deck> list() {
        return deckService.list();
    }

    @Command(command = "edit", description = "Edit the name of a specific deck.")
    public void edit(String name, String newName) {
        deckService.edit(name, newName);
    }

    @Command(command = "delete", description = "Delete a specific deck.")
    public void delete(String name) {
        deckService.delete(name);
    }
}
