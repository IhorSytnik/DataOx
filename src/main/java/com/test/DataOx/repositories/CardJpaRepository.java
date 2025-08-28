package com.test.DataOx.repositories;

import com.test.DataOx.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardJpaRepository extends JpaRepository<Card, Long> {
}
