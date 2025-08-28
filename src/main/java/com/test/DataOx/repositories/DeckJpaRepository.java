package com.test.DataOx.repositories;

import com.test.DataOx.model.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeckJpaRepository extends CrudRepository<Deck, Long> {
    @Transactional
    void deleteByName(String name);
    Deck findByName(String name);
}
