package com.cavernasedragoes.domain.repository;

import com.cavernasedragoes.domain.model.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {

}
