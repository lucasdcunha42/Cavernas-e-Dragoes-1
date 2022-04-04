package com.cavernasedragoes.api.controller;

import com.cavernasedragoes.domain.exception.EntidadeNaoEncontradaException;
import com.cavernasedragoes.domain.model.Classe;
import com.cavernasedragoes.domain.repository.ClasseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/{classeId}")
    @ApiOperation(value=" Retorna Classe especifica pelo id")
    public ResponseEntity<Classe> buscar(@PathVariable Long classeId) {
        Optional<Classe> classe = classeRepository.findById(classeId);

        if (classe.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(classe.get());
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
    @PutMapping("/{classeId}")
    @ApiOperation(value= "Atualiza um Classe")
    public ResponseEntity<?> atualizar(@PathVariable Long classeId,
                                       @RequestBody Classe novaClasse){
        try {
            Optional<Classe> classeAtual = classeRepository.findById(classeId);

            if (classeAtual.isPresent()) {
                BeanUtils.copyProperties(novaClasse, classeAtual.get(),"id");
                classeAtual = Optional.ofNullable(classeRepository.save(classeAtual.get()));
                return ResponseEntity.ok(classeAtual);
            }
            return  ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PatchMapping("/{classeId")
    @ApiOperation(value="Atualiza uma classe parcialmente")
    public  ResponseEntity<?> atualizarParcial(@PathVariable Long classeId,
                                               @RequestBody Map<String, Object> campos) {
        Optional<Classe> classeAtual = classeRepository.findById(classeId);

        if (classeAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        merge(campos, classeAtual.get());

        return atualizar(classeId, classeAtual.get());
    }
    private void merge(Map<String, Object> dadosOrigem, Classe classeDestino){
        ObjectMapper objectMapper = new ObjectMapper();
        Classe classeOrigem = objectMapper.convertValue(dadosOrigem, Classe.class);

        dadosOrigem.forEach((nomePropiedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Classe.class, nomePropiedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, classeOrigem);

            ReflectionUtils.setField(field, classeDestino, novoValor);
        });
    }
    @DeleteMapping("/{classeId}")
    @ApiOperation(value= "Deleta uma Classe")
    public ResponseEntity<Classe> deletarPorId (@PathVariable Long classeId) {
        Optional<Classe> classe = classeRepository.findById(classeId);

        if (classe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        classeRepository.delete(classe.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
