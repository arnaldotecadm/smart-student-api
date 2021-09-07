package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.ListaPresenca;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.ListaPresencaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class ListaPresencaService implements RestBasicService<ListaPresenca> {

    private final ListaPresencaRepository repository;

    @Autowired
    public ListaPresencaService(ListaPresencaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ListaPresenca> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<ListaPresenca> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public ListaPresenca save(ListaPresenca t) {
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

    public List<ListaPresenca> getByTurma(String turmaId) throws ExecutionException, InterruptedException {
        return this.repository.getByTurma(turmaId);
    }
}
