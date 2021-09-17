package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.Aluno;
import br.com.smartstudent.api.model.Atividade;
import br.com.smartstudent.api.model.Turma;
import br.com.smartstudent.api.model.Usuario;
import br.com.smartstudent.api.service.AlunoService;
import br.com.smartstudent.api.service.AtividadeService;
import br.com.smartstudent.api.service.TurmaService;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("atividade")
@CrossOrigin("*")
public class AtividadeController extends RestBasicController<Atividade> {

    private AtividadeService service;
    private AlunoService alunoService;

    @Autowired
    public AtividadeController(AtividadeService basicService, AlunoService alunoService) {
        super(basicService);
        service = basicService;
        this.alunoService = alunoService;
    }

    @GetMapping("atividades-por-turma/{turmaId}")
    public List<Atividade> getAtividadesByTurma(@PathVariable("turmaId") String turmaId) throws ExecutionException, InterruptedException {
        List<Atividade> atividadesByTurma = this.service.getAtividadesByTurma(turmaId);
        Collections.sort(atividadesByTurma, Comparator.comparingInt(Atividade::getOrdemApresentacao));
        return atividadesByTurma;
    }

    @GetMapping("atividades-por-aluno")
    public List<Atividade> getAtividadesByAluno() throws ExecutionException, InterruptedException {
        Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Aluno> byId = this.alunoService.getByUsuario(usuario.getUid());
        if(byId.isPresent()){
            Aluno aluno = byId.get();
            return this.service.getAtividadesByTurma(aluno.getTurma());
        }
        return Collections.emptyList();
    }

    @PostMapping("disponibilizar")
    public Atividade disponibilizarAtividade(@RequestBody String turmaId) throws ExecutionException, InterruptedException {
        return this.service.disponibilizarAtividade(turmaId);
    }

    @PostMapping("indisponibilizar")
    public Atividade indisponibilizarAtividade(@RequestBody String turmaId) throws ExecutionException, InterruptedException {
        return this.service.indisponibilizarAtividade(turmaId);
    }
}
