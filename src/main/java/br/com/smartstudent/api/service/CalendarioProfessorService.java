package br.com.smartstudent.api.service;

import br.com.smartstudent.api.model.CalendarioProfessor;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.CalendarioProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class CalendarioProfessorService implements RestBasicService<CalendarioProfessor>{

    private final CalendarioProfessorRepository repository;

    @Autowired
    public CalendarioProfessorService(CalendarioProfessorRepository repository){
        this.repository = repository;
    }

    @Override
    public List<CalendarioProfessor> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<CalendarioProfessor> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public CalendarioProfessor save(CalendarioProfessor t) {
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
