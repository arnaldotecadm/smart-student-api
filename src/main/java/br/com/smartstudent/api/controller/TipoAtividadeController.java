package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.TipoAtividade;
import br.com.smartstudent.api.service.TipoAtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("atividade")
@CrossOrigin("*")
public class TipoAtividadeController extends RestBasicController<TipoAtividade> {

    @Autowired
    public TipoAtividadeController(TipoAtividadeService basicService) {
        super(basicService);
    }
}
