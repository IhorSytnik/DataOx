package com.test.DataOx.controllers;

import com.test.DataOx.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.component.StringInput;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.standard.AbstractShellComponent;

@Command(group = "Card commands", command = "card")
public class CardControllerImpl extends AbstractShellComponent {

    @Autowired
    private ComponentFlow.Builder componentFlowBuilder;

    @Command(command = "create")
    public void create(String deckName, String question, String answer) {
        flowTest();
    }

    public Iterable<Card> list() {
        return null;
    }

    public void edit(String name, String newName) {

    }

    public void delete(String name) {

    }

    public String flowTest() {
        StringInput component = new StringInput(getTerminal(), "Enter value", "myvalue");
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
        return "Got value " + context.getResultValue();
    }
}
