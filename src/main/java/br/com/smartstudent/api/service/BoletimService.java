package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.Boletim;
import br.com.smartstudent.api.model.Turma;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.BoletimRepository;
import br.com.smartstudent.api.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class BoletimService implements RestBasicService<Boletim>{

    private final BoletimRepository repository;

    @Autowired
    public BoletimService(BoletimRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Boletim> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<Boletim> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public Boletim save(Boletim t) {
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

    public List<Boletim> getBoletimByTurma(String turma) throws ExecutionException, InterruptedException {
        return this.repository.getBoletimByTurma(turma);
    }
}
