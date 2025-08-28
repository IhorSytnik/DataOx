package com.test.DataOx.controllers;

import com.test.DataOx.exceptions.ElementAlreadyExistsException;
import com.test.DataOx.model.Deck;
import com.test.DataOx.services.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

@Command(group = "Deck commands", command = "deck")
public class DeckController {
    @Autowired
    private DeckService deckService;
    @Autowired
    private Flow flow;

    /**
     * Creates a new deck with the given name.
     *
     * @param name name of the new deck.
     * @return "Success" text if the deck has been saved, or an error message, otherwise.
     */
    @Command(command = "create", description = "Create a new deck with a given name.")
    public String create(String name) {
        try {
            deckService.create(name);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Success";
    }

    /**
     * Provides a list of all decks.
     *
     * @return list of all decks.
     */
    @Command(command = "list", description = "List all decks.")
    public Iterable<Deck> list() {
        return deckService.list();
    }

    /**
     * Changes the given deck's name for the new one.
     *
     * @param name name of the deck whose name will be changed.
     * @param newName new deck name.
     */
    @Command(command = "edit", description = "Edit the name of a specific deck.")
    public void edit(String name, String newName) {
        deckService.edit(name, newName);
    }

    /**
     * Deletes the specified deck.
     *
     * @param name name of the deck to be deleted,
     */
    @Command(command = "delete", description = "Delete a specific deck.")
    public void delete(String name) {
        deckService.delete(name);
    }

    /**
     * Starts the Card Management mode for a specific deck.
     *
     * @param name name of the deck.
     * @return object whose ToString method result will be used in the output.
     * @see Flow#cardManagementFlow(String)
     */
    @Command(description = "Card management menu for a specific deck.")
    public Object deck(String name) {
        return flow.cardManagementFlow(name);
    }

    /**
     * Starts Quiz Mode.
     *
     * @param name name of the deck for the quiz.
     * @return final score text.
     * @see Flow#quizModeFlow(String)
     */
    @Command(command = "quiz", description = "Start quiz mode.")
    public String quiz(String name) {
        return "Final score: " + flow.quizModeFlow(name);
    }
}
