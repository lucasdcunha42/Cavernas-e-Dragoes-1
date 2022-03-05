package com.cavernasedragoes.domain.service;

import com.cavernasedragoes.domain.model.Raca;
import com.cavernasedragoes.domain.repository.RacaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRacaService {

    @Autowired
    private RacaRepository racaRepository;

    public Raca salvar(Raca raca) {
        return racaRepository.save(raca);
    }

}