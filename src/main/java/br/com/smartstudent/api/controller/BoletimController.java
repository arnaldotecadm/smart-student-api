package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.*;
import br.com.smartstudent.api.service.AtividadeService;
import br.com.smartstudent.api.service.BoletimService;
import br.com.smartstudent.api.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("boletim")
@CrossOrigin("*")
public class BoletimController extends RestBasicController<Boletim> {

    private BoletimService service;
    private AtividadeService atividadeService;

    @Autowired
    public BoletimController(BoletimService basicService, AtividadeService atividadeService) {
        super(basicService);
        service = basicService;
        this.atividadeService = atividadeService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Boletim> save(@RequestBody Boletim t) throws ExecutionException, InterruptedException {
        List<Boletim> boletimList = this.service.getBoletimByTurma(t.getTurma());
        boletimList.forEach( item -> {
            this.service.deleteById(item.getDocumentId());
        });

        List<Nota> notaList = t.getNotas().stream().filter(item -> !item.getAtividade().equalsIgnoreCase("media")).collect(Collectors.toList());
        t.setNotas(notaList);

        return super.save(t);
    }

    @GetMapping("boletim-por-turma/{turmaId}")
    public List<Boletim> getBoletimPorTurma(@PathVariable("turmaId") String turmaId) throws ExecutionException, InterruptedException {

        List<Boletim> boletimByTurma = this.service.getBoletimByTurma(turmaId);

        Map<String, List<Atividade>> atividadeList = this.atividadeService.getAll().stream().collect(Collectors.groupingBy(Atividade::getDocumentId));

        Map<String, List<Nota>> grupoNotaPorAluniList = boletimByTurma.get(0)
                .getNotas()
                .stream()
                .collect(Collectors.groupingBy(Nota::getUsuario));

        grupoNotaPorAluniList
                .forEach((aluno, items) -> {
                    Nota reduce = items.stream().reduce(new Nota(), (partialNota, nota) -> {
                        Atividade atividade = atividadeList.get(nota.getAtividade()).get(0);
                        double notaAtual = 0;
                        if(atividade.isDesconsiderarNotaMaxima()){
                            notaAtual = partialNota.getNota() + nota.getNota();
                        } else{
                            notaAtual = partialNota.getNota() + (nota.getNota() * (atividade.getNotaMaxima() / 10));
                        }

                        return new Nota(aluno, "media", notaAtual);
                    });

                    boletimByTurma.get(0).getNotas().add(reduce);
                });

        return boletimByTurma;
    }
}