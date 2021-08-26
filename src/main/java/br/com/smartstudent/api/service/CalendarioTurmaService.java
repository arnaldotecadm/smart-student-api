package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.CalendarioTurma;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.CalendarioTurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class CalendarioTurmaService implements RestBasicService<CalendarioTurma>{

    private final CalendarioTurmaRepository repository;

    @Autowired
    public CalendarioTurmaService(CalendarioTurmaRepository repository){
        this.repository = repository;
    }

    @Override
    public List<CalendarioTurma> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<CalendarioTurma> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public CalendarioTurma save(CalendarioTurma t) {
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
