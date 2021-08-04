package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.Aluno;
import br.com.smartstudent.api.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aluno")
public class AlunoController extends RestBasicController<Aluno> {

    @Autowired
    public AlunoController(AlunoService basicService) {
        super(basicService);
    }
}
