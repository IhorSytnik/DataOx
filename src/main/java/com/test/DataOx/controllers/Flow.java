package com.test.DataOx.controllers;

import com.test.DataOx.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.flow.SelectItem;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class Flow extends AbstractShellComponent {

    @Autowired
    private CardController cardController;
    @Autowired
    private ComponentFlow.Builder componentFlowBuilder;

    /**
     * Opens the Quiz flow.
     * Iterates over all the cards in the deck asking the specified questions and comparing the answers.
     * At the end of the quiz provides the final score.
     *
     * @param deckName name of the deck for the quiz.
     * @return score.
     */
    public Integer quizModeFlow(String deckName) {
        Iterable<Card> cards = cardController.list(deckName);
        Iterator<Card> iterator = cards.iterator();
        AtomicInteger score = new AtomicInteger(0);

        if (!iterator.hasNext())
            return score.get();

        AtomicBoolean continueFlag = new AtomicBoolean(true);
        while (continueFlag.get() && iterator.hasNext()) {
            AtomicReference<Card> card = new AtomicReference<>(iterator.next());

            ComponentFlow flow = componentFlowBuilder.clone().reset()
                .withStringInput("question")
                    .name("Current score: " + score.get() + " | Question: " + card.get().getQuestion())
                    .next(s -> {
                        if (card.get().getAnswer().equalsIgnoreCase(s.getMaskedInput())) {
                            score.getAndIncrement();
                            return "correct";
                        }
                        return "wrong";
                    })
                    .and()
                .withConfirmationInput("wrong")
                    .name("Wrong! The correct answer was: " + card.get().getAnswer() + " | Continue?")
                    .postHandler(confirmationInputContext -> continueFlag.set(confirmationInputContext.getInput().equalsIgnoreCase("y")))
                    .next(s -> "")
                    .and()
                .withConfirmationInput("correct")
                    .name("Correct! | Continue?")
                    .postHandler(confirmationInputContext -> continueFlag.set(confirmationInputContext.getInput().equalsIgnoreCase("y")))
                    .and()
                .build();
            flow.run();
        }

        return score.get();
    }

    /**
     * Opens the Card Management flow for a specific deck.
     * Possible options:
     * <ul>
     * <li>`create`: provide question and answer for a new card in the deck;
     * <li> `list`: list all cards in the deck;
     * <li> `edit`: provide new question and answer for a card in the deck;
     * <li> `delete`: choose a card to delete;
     * <li> `cancel`: exit this flow.
     * </ul>
     *
     * @param deckName name of the deck to manage the cards of.
     * @return object whose ToString method result will be used in the output.
     */
    public Object cardManagementFlow(String deckName) {
        AtomicReference<Object> toReturn = new AtomicReference<>(new Object());
        List<SelectItem> menuItems = Arrays.asList(
            SelectItem.of("create", "create"),
            SelectItem.of("list", "list"),
            SelectItem.of("edit", "edit"),
            SelectItem.of("delete", "delete"),
            SelectItem.of("cancel", "cancel"));
        AtomicReference<String> questionToSave = new AtomicReference<>("");
        AtomicReference<String> answerToSave = new AtomicReference<>("");
        AtomicReference<Long> toEditId = new AtomicReference<>(-1L);

        ComponentFlow flow = componentFlowBuilder.clone().reset()
            .withSingleItemSelector("menu")
                .name("Choose action")
                .selectItems(menuItems)
                .postHandler((command) -> {
                    toReturn.set("");
                    switch (command.getValue().get()) {
                        case "list":
                            toReturn.set(cardController.list(deckName));
                            break;
                        case "delete":
                            break;
                    }
                })
                .next((command) -> switch (command.getValue().get()) {
                    case "delete" -> "delete";
                    case "create" -> "crateQuestion";
                    case "edit" -> "edit";
                    case "cancel", "list" -> "";
                    default -> "menu";
                })
                .and()
            .withSingleItemSelector("delete")
                .name("Choose a card to delete:")
                .selectItems(getCardListAsItems(deckName))
                .postHandler(s -> cardController.delete(s.getValue().get()))
                .next(str -> "menu")
                .and()
            .withStringInput("crateQuestion")
                .name("Enter card's question:")
                .postHandler((command) -> questionToSave.set(command.getMaskedInput()))
                .next(str -> "crateAnswer")
                .and()
            .withStringInput("crateAnswer")
                .name("Enter card's answer:")
                .postHandler((command) -> answerToSave.set(command.getMaskedInput()))
                .next(str -> "menu")
                .postHandler(stringInputContext -> cardController.create(deckName, questionToSave.get(), answerToSave.get()))
                .and()
            .withSingleItemSelector("edit")
                .name("Choose a card to edit:")
                .selectItems(getCardListAsItems(deckName))
                .postHandler(s -> toEditId.set(Long.valueOf(s.getValue().get())))
                .next(str -> "editQuestion")
                .and()
            .withStringInput("editQuestion")
                .name("Enter card's question:")
                .postHandler((command) -> questionToSave.set(command.getMaskedInput()))
                .next(str -> "editAnswer")
                .and()
            .withStringInput("editAnswer")
                .name("Enter card's answer:")
                .postHandler((command) -> answerToSave.set(command.getMaskedInput()))
                .next(str -> "menu")
                .postHandler(stringInputContext -> cardController.edit(toEditId.get(), questionToSave.get(), answerToSave.get()))
            .and()
            .build();
        flow.run();
        return toReturn;
    }

    /**
     * Builds {@link SelectItem} list for SingleItemSelector of all cards of the given deck.
     *
     * @param deckName deck name for the list of cards.
     * @return {@link SelectItem} list for SingleItemSelector of all cards of the given deck.
     */
    private List<SelectItem> getCardListAsItems(String deckName) {
        return StreamSupport.stream(cardController.list(deckName).spliterator(), false)
            .map(card -> SelectItem.of(card.toString(), String.valueOf(card.getId())))
            .collect(Collectors.toList());
    }
}
