package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.Aluno;
import br.com.smartstudent.api.model.Atividade;
import br.com.smartstudent.api.model.Turma;
import br.com.smartstudent.api.service.AtividadeService;
import br.com.smartstudent.api.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("atividade")
@CrossOrigin("*")
public class AtividadeController extends RestBasicController<Atividade> {

    private AtividadeService service;

    @Autowired
    public AtividadeController(AtividadeService basicService) {
        super(basicService);
        service = basicService;
    }

    @GetMapping("atividades-por-turma/{turmaId}")
    public List<Atividade> getAtividadesByTurma(@PathVariable("turmaId") String turmaId) throws ExecutionException, InterruptedException {
        return this.service.getAtividadesByTurma(turmaId);
    }
}
