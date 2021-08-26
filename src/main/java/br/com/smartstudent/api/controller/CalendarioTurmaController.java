package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.CalendarioTurma;
import br.com.smartstudent.api.service.CalendarioTurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calendario-turma")
@CrossOrigin("*")
public class CalendarioTurmaController extends RestBasicController<CalendarioTurma> {

    @Autowired
    public CalendarioTurmaController(CalendarioTurmaService basicService) {
        super(basicService);
    }
}
