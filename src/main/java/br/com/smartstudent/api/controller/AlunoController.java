package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.Aluno;
import br.com.smartstudent.api.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("aluno")
@CrossOrigin("*")
public class AlunoController extends RestBasicController<Aluno> {

    private AlunoService service;

    @Autowired
    public AlunoController(AlunoService basicService) {
        super(basicService);
        this.service = basicService;
    }

    @GetMapping("aluno-por-turma/{turmaId}")
    public List<Aluno> getAlunosPorTurma(@PathVariable("turmaId") String turmaId) throws ExecutionException, InterruptedException {
        return this.service.getAlunosByTurma(turmaId);
    }
}
