package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.Materia;
import br.com.smartstudent.api.model.Turma;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.MateriaRepository;
import br.com.smartstudent.api.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class MateriaService implements RestBasicService<Materia>{

    private final MateriaRepository repository;

    @Autowired
    public MateriaService(MateriaRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Materia> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<Materia> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public Materia save(Materia t) {
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

}
