package com.test.DataOx.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Card {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Deck deck;

    private String question;
    private String answer;

    public Card(Deck deck, String question, String answer) {
        this.deck = deck;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return question + " | " + answer;
    }
}
