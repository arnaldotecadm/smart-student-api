package br.com.smartstudent.api.service;

import br.com.smartstudent.api.enums.StatusAtividadeEnum;
import br.com.smartstudent.api.model.Aluno;
import br.com.smartstudent.api.model.Atividade;
import br.com.smartstudent.api.model.Professor;
import br.com.smartstudent.api.model.Turma;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.AtividadeRepository;
import br.com.smartstudent.api.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class AtividadeService implements RestBasicService<Atividade>{

    private final AtividadeRepository repository;

    @Autowired
    public AtividadeService(AtividadeRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Atividade> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<Atividade> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public Atividade save(Atividade t) {
        return this.repository.save(t);
    }

    @Override
    public void deleteById(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public AbstractFirebaseRepository getRespository() {
        return repository;
    }

    public List<Atividade> getAtividadesByTurma(String turmaId) throws ExecutionException, InterruptedException {
        return this.repository.getAtividadesByTurma(turmaId);
    }

    public Atividade disponibilizarAtividade(String atividadeId) throws ExecutionException, InterruptedException {
        return this.setStatusAtividade(atividadeId, StatusAtividadeEnum.DISPONIBILIZADA);
    }

    public Atividade indisponibilizarAtividade(String atividadeId) throws ExecutionException, InterruptedException {
        return this.setStatusAtividade(atividadeId, StatusAtividadeEnum.INDISPONIBILIZADA);
    }

    private Atividade setStatusAtividade(String atividadeId, StatusAtividadeEnum statusAtividadeEnum) throws ExecutionException, InterruptedException {
        Optional<Atividade> byId = this.getById(atividadeId);
        if(byId.isPresent()){
            byId.get().setStatusAtividadeEnum(statusAtividadeEnum);
            Atividade save = this.save(byId.get());
            return save;
        }
        return null;
    }
}
