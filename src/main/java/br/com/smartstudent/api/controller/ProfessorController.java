package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.Professor;
import br.com.smartstudent.api.model.Turma;
import br.com.smartstudent.api.service.ProfessorService;
import br.com.smartstudent.api.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("professor")
@CrossOrigin("*")
public class ProfessorController extends RestBasicController<Professor> {

    @Autowired
    public ProfessorController(ProfessorService basicService) {
        super(basicService);
    }
}
