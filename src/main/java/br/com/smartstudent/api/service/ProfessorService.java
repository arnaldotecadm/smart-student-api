package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.Professor;
import br.com.smartstudent.api.model.Turma;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.ProfessorRepository;
import br.com.smartstudent.api.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class ProfessorService implements RestBasicService<Professor>{

    private final ProfessorRepository repository;

    @Autowired
    public ProfessorService(ProfessorRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Professor> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<Professor> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public Professor save(Professor t) {
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

    public Optional<Professor> getByUsuario(String usuario) throws ExecutionException, InterruptedException {
        return this.repository.getByUsuario(usuario);
    }
}
