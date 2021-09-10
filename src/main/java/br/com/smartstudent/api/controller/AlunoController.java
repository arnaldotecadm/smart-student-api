package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.Aluno;
import br.com.smartstudent.api.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("aluno-por-uid/{usuarioUid}")
   public ResponseEntity getAlunoByUsuarioUid(@PathVariable("usuarioUid") String usuarioUid) throws ExecutionException, InterruptedException {
        Optional<Aluno> byUsuario = this.service.getByUsuario(usuarioUid);
        Aluno aluno = byUsuario.orElseGet(null);
        return ResponseEntity.ok(aluno);
    }
}
