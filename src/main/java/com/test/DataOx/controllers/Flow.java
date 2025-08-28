package com.test.DataOx.controllers;

import com.test.DataOx.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.component.StringInput;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.flow.SelectItem;
import org.springframework.shell.standard.AbstractShellComponent;

import java.util.Arrays;
import java.util.List;

@Command(group = "Card commands", command = "card")
public class CardControllerImpl extends AbstractShellComponent implements CommonCrudController<Card> {

    @Autowired
    private ComponentFlow.Builder componentFlowBuilder;

    @Override
    @Command(command = "create")
    public void create(@Option(required = false) String name) {
        flowTest();
    }

    @Override
    public Iterable<Card> list() {
        return null;
    }

    @Override
    public void edit(String name, String newName) {

    }

    @Override
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
