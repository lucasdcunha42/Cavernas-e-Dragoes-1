package com.cavernasedragoes.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cavernasedragoes.domain.exception.EntidadeNaoEncontradaException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.cavernasedragoes.domain.model.Raca;
import com.cavernasedragoes.domain.repository.RacaRepository;
import com.cavernasedragoes.domain.service.CadastroRacaService;


@RestController
@RequestMapping(value = "/racas")
@Api(value = "API Raças")
public class RacaController {

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private CadastroRacaService cadastroRaca;

    @GetMapping
    @ApiOperation(value=" Retorna uma lista de todas as raças.")
    public List<Raca> listar() {
        return racaRepository.findAll();
    }

    @GetMapping("/{racaId}")
    @ApiOperation(value=" Retorna a raça especificada pelo id")
    public ResponseEntity<Raca> buscar(@PathVariable Long racaId) {
        Optional<Raca> raca = racaRepository.findById(racaId);

        if(raca.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(raca.get());
    }

    @PostMapping
    @ApiOperation(value="Cria uma nova raça, retornando a mesma")
    public ResponseEntity<?> adicionar(@RequestBody Raca raca) {
        try {
            raca = cadastroRaca.salvar(raca);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(raca);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{racaId}")
    @ApiOperation(value="Atualiza uma raça, retornando a mesma")
    public ResponseEntity<?> atualizar(@PathVariable Long racaId,
                                       @RequestBody Raca novaRaca) {
        try {
            Optional<Raca> racaAtual = racaRepository.findById(racaId);

            if (racaAtual != null) {
                BeanUtils.copyProperties(novaRaca, racaAtual.get(), "id");
                racaAtual = Optional.ofNullable(cadastroRaca.salvar(racaAtual.get()));
                return ResponseEntity.ok(racaAtual);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{racaId}")
    @ApiOperation(value="Atualiza uma raça parcialmente, retornando a mesma")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long racaId,
                                              @RequestBody Map<String, Object> campos) {
        Optional<Raca> racaAtual = racaRepository.findById(racaId);

        if (racaAtual == null) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, racaAtual.get());

        return atualizar(racaId, racaAtual.get());
    }

    private void merge(Map<String, Object> dadosOrigem, Raca racaDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Raca racaOrigem = objectMapper.convertValue(dadosOrigem, Raca.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Raca.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, racaOrigem);

            ReflectionUtils.setField(field, racaDestino, novoValor);
        });
    }

    @DeleteMapping("/{racaId}")
    @ApiOperation(value=" Deleta uma raça")
    public ResponseEntity<Raca> deletarPorId(@PathVariable Long racaId) {
        Optional<Raca> raca = racaRepository.findById(racaId);

        if(raca.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        racaRepository.delete(raca.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
