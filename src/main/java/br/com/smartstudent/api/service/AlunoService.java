package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.Aluno;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class AlunoService implements RestBasicService<Aluno>{

    private final AlunoRepository repository;

    @Autowired
    public AlunoService(AlunoRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Aluno> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<Aluno> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public Aluno save(Aluno aluno) {
        return this.repository.save(aluno);
    }

    @Override
    public void deleteById(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public AbstractFirebaseRepository getRespository() {
        return repository;
    }

    public List<Aluno> getAlunosByTurma(String turmaId) throws ExecutionException, InterruptedException {
        return this.repository.getAlunosByTurma(turmaId);
    }
}
