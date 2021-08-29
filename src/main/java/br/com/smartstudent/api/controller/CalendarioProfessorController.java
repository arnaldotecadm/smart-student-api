package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.CalendarioProfessor;
import br.com.smartstudent.api.service.CalendarioProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("calendario-professor")
@CrossOrigin("*")
public class CalendarioProfessorController extends RestBasicController<CalendarioProfessor> {

    @Autowired
    public CalendarioProfessorController(CalendarioProfessorService basicService) {
        super(basicService);
    }

    @GetMapping(value = "all/{documentId}")
    public List<CalendarioProfessor> getAll(@PathVariable("documentId") String documentId) throws ExecutionException, InterruptedException {
        List<CalendarioProfessor> professorList = super.getAll();
        return professorList.stream().filter(item -> item.getProfessor().equals(documentId)).collect(Collectors.toList());
    }
}
