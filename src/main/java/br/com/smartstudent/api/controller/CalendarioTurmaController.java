package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.CalendarioProfessor;
import br.com.smartstudent.api.model.CalendarioTurma;
import br.com.smartstudent.api.service.CalendarioTurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("calendario-turma")
@CrossOrigin("*")
public class CalendarioTurmaController extends RestBasicController<CalendarioTurma> {

    @Autowired
    public CalendarioTurmaController(CalendarioTurmaService basicService) {
        super(basicService);
    }

    @GetMapping(value = "all/{documentId}")
    public List<CalendarioTurma> getAll(@PathVariable("documentId") String documentId) throws ExecutionException, InterruptedException {
        List<CalendarioTurma> listagem = super.getAll();
        return listagem.stream().filter(item -> item.getTurma().equals(documentId)).collect(Collectors.toList());
    }
}
