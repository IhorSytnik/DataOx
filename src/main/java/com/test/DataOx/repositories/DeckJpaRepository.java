package com.test.DataOx.repositories;

import com.test.DataOx.model.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DeckJpaRepository extends CrudRepository<Deck, Long> {
    @Transactional
    void deleteByName(String name);
    Optional<Deck> findByName(String name);
}
