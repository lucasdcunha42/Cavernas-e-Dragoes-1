package com.cavernasedragoes.api.controller;

import com.cavernasedragoes.domain.exception.EntidadeNaoEncontradaException;
import com.cavernasedragoes.domain.model.Classe;
import com.cavernasedragoes.domain.repository.ClasseRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/classes")
@Api(value = "API Classes")
public class ClasseController {

    @Autowired
    private ClasseRepository classeRepository;

    @GetMapping
    @ApiOperation(value=" Retorna uma lista de todas as Classes")
    public List<Classe> listar() {
        return classeRepository.findAll();
    }

    @PostMapping
    @ApiOperation(value = "Cria Classe")
        public ResponseEntity<?> adicionar(@RequestBody Classe classe) {
            try {
                classe = classeRepository.save(classe);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(classe);
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.badRequest()
                        .body(e.getMessage());
            }
    }

}
