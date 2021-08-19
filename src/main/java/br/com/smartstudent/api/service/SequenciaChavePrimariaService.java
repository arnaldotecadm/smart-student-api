package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.SequenciaChavePrimaria;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.SequenciaChavePrimariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class SequenciaChavePrimariaService implements RestBasicService<SequenciaChavePrimaria>{

    private final SequenciaChavePrimariaRepository repository;

    @Autowired
    public SequenciaChavePrimariaService(SequenciaChavePrimariaRepository repository){
        this.repository = repository;
    }

    @Override
    public List<SequenciaChavePrimaria> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<SequenciaChavePrimaria> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public SequenciaChavePrimaria save(SequenciaChavePrimaria t) {
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

    public List<SequenciaChavePrimaria> getLastKey(String nomeTabela) throws ExecutionException, InterruptedException {
        return this.repository.getLastKey(nomeTabela);
    }
}
