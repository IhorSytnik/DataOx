# Flashcard Quiz App

> [!IMPORTANT]
> **THIS APPLICATION CANNOT BE TESTED AND RAN IN THE INTELLIJ RUN TOOL. PACKAGE IT FIRST WITH MAVEN AND TEST IT IN A REGULAR TERMINAL.**

## Available commands:
### Built-In Commands (Spring Shell commands)
- `help`: Display help about available commands
- `stacktrace`: Display the full stacktrace of the last error.
- `clear`: Clear the shell screen.
- `quit`, `exit`: Exit the shell.
- `history`: Display or save the history of previously run commands
- `version`: Show version info
- `script`: Read and execute commands from a file.

### Card commands
- `card list <deckName>`: List all cards in a specific deck.
- `card create <deckName> <question> <answer>`: Create a card for a specific deck.

### Deck commands
- `deck delete <name>`: Delete a specific deck.
- `deck create <name>`: Create a new deck with a given name.
- `deck edit <name> <newName>`: Edit the name of a specific deck.
- `deck list`: List all decks.
- `deck quiz <name>`: Start quiz mode.
- `deck <name>`: Card management menu for a specific deck.

## Sub commands
The following commands initialize spring shell component. 

- `deck quiz <name>`: Start quiz mode. Iterates over all the cards in the deck asking the specified questions and comparing the answers. At the end of the quiz prints the final score.

- `deck <name>`: Card management menu for a specific deck. You can choose one of the following commands:
  - `create`: provide question and answer for a new card in the deck;
  - `list`: list all cards in the deck;
  - `edit`: provide new question and answer for a card in the deck;
  - `delete`: choose a card to delete;
  - `cancel`: exit this flow.
