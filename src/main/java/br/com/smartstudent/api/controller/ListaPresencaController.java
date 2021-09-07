package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.ListaPresenca;
import br.com.smartstudent.api.model.Turma;
import br.com.smartstudent.api.service.ListaPresencaService;
import br.com.smartstudent.api.service.TurmaService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("lista-presenca")
@CrossOrigin("*")
public class ListaPresencaController extends RestBasicController<ListaPresenca> {

    private ListaPresencaService basicService;

    @Autowired
    public ListaPresencaController(ListaPresencaService basicService) {
        super(basicService);
        this.basicService = basicService;
    }

    @PostMapping("add-all-aluno")
    public ResponseEntity savePresenceList(@RequestBody ListaPresenca listaPresenca) throws ExecutionException, InterruptedException {
        ResponseEntity<ListaPresenca> save = this.save(listaPresenca);
        return ResponseEntity.ok(save);
    }

    @GetMapping("by-turma/{turmaId}")
    public ResponseEntity getByTurma(@PathVariable("turmaId") String turmaId) throws ExecutionException, InterruptedException {
        List<ListaPresenca> byTurma = this.basicService.getByTurma(turmaId);
        return ResponseEntity.ok(byTurma);
    }
}

